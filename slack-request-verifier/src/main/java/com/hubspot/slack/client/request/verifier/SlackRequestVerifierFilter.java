package com.hubspot.slack.client.request.verifier;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.apache.commons.codec.binary.Hex;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.hash.Hashing;
import com.google.common.io.CharStreams;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * Check for detailed information https://api.slack.com/authentication/verifying-requests-from-slack
 */
public class SlackRequestVerifierFilter implements ContainerRequestFilter {

  private static final String SLACK_TIMESTAMP_HEADER = "X-Slack-Request-Timestamp";
  private static final String SLACK_SIGNATURE_HEADER = "X-Slack-Signature";
  private static final String CONCATENATED_SIGN_TEMPLATE = "%s:%s:%s";
  private static final String ENCODED_SIGNATURE_TEMPLATE = "%s=%s";
  private static final String VERSION_NUMBER = "v0";

  private final long requestExpirationTime;
  private final String signingSecret;

  public interface Factory {
    SlackRequestVerifierFilter create(
        @Assisted("requestExpirationTime") long requestExpirationTime,
        @Assisted("signingSecret") String signingSecret
    );
  }

  @Inject
  protected SlackRequestVerifierFilter(
      @Assisted("requestExpirationTime") long requestExpirationTime,
      @Assisted("signingSecret") String signingSecret
  ) {
    this.requestExpirationTime = requestExpirationTime;
    this.signingSecret = signingSecret;
  }

  @Override
  public void filter(ContainerRequestContext requestContext) {
    List<String> slackTimestampHeaders = requestContext
        .getHeaders()
        .get(SLACK_TIMESTAMP_HEADER);
    List<String> slackSignatureHeaders = requestContext
        .getHeaders()
        .get(SLACK_SIGNATURE_HEADER);

    Preconditions.checkNotNull(
        slackTimestampHeaders,
        "Missing header " + SLACK_TIMESTAMP_HEADER
    );
    Preconditions.checkNotNull(
        slackSignatureHeaders,
        "Missing header " + SLACK_SIGNATURE_HEADER
    );

    Preconditions.checkArgument(
        slackTimestampHeaders.size() == 1,
        "Expected single header " + SLACK_TIMESTAMP_HEADER
    );
    Preconditions.checkArgument(
        slackSignatureHeaders.size() == 1,
        "Expected single header " + SLACK_SIGNATURE_HEADER
    );

    validateRequest(
        getRequestBody(requestContext),
        slackTimestampHeaders.get(0),
        slackSignatureHeaders.get(0)
    );
  }

  private String getRequestBody(ContainerRequestContext requestContext) {
    try (InputStream inputStream = requestContext.getEntityStream()) {
      String body = CharStreams.toString(
          new InputStreamReader(inputStream, Charsets.UTF_8)
      );

      if (inputStream instanceof ByteArrayInputStream) {
        inputStream.reset();
      } else {
        requestContext.setEntityStream(new ByteArrayInputStream(body.getBytes(Charsets.UTF_8)));
      }

      return body;
    } catch (IOException exception) {
      throw new RuntimeException(
          String.format(
              "Encountered exception reading bytes from body of request %s",
              requestContext
          ),
          exception
      );
    }
  }

  private void validateRequest(
      String body,
      String slackTimestampHeader,
      String slackSignatureHeader
  ) {
    checkRequestExpired(Long.valueOf(slackTimestampHeader));
    checkRequestSignature(body, slackTimestampHeader, slackSignatureHeader);
  }

  private void checkRequestExpired(Long requestTimestamp) {
    Preconditions.checkArgument(
        ((System.currentTimeMillis() / 1000) - requestTimestamp) < requestExpirationTime,
        "Request has expired"
    );
  }

  private void checkRequestSignature(
      String body,
      String slackTimestampHeader,
      String slackSignatureHeader
  ) {
    String concatenatedSignature = String.format(CONCATENATED_SIGN_TEMPLATE, VERSION_NUMBER, slackTimestampHeader, body);
    String encodedSignature = String.format(
        ENCODED_SIGNATURE_TEMPLATE,
        VERSION_NUMBER,
        getEncodedSignature(concatenatedSignature)
    );

    Preconditions.checkArgument(
        encodedSignature.equals(slackSignatureHeader),
        "Invalid signature"
    );
  }

  private String getEncodedSignature(String signature) {
    return Hex.encodeHexString(
        Hashing
            .hmacSha256(signingSecret.getBytes(Charsets.UTF_8))
            .hashString(signature, Charsets.UTF_8)
            .asBytes()
    );
  }
}

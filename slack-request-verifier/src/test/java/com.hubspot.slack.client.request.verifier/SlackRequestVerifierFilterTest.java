package com.hubspot.slack.client.request.verifier;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.junit.Before;
import org.junit.Test;

public class SlackRequestVerifierFilterTest {

  private static final String SIGNING_SECRET = "secret";
  private static final long REQUEST_EXPIRATION_TIME = 1;
  private static final String SIGNATURE =
    "v0=1a7f6461d46b08d647f4992b34d9264768a3f905e61ef596d733f55673691427";
  private static final String SLACK_TIMESTAMP_HEADER = "X-Slack-Request-Timestamp";
  private static final String SLACK_SIGNATURE_HEADER = "X-Slack-Signature";
  private static final String HEADER_EMPTY_ERROR_TEMPLATE = "Missing header %s";
  private static final String EXPECTED_SINGLE_HEADER_ERROR_TEMPLATE =
    "Expected single header %s";
  private static final String GET_REQUEST_BODY_ERROR_TEMPLATE =
    "Encountered exception reading bytes from body of request %s";
  private static final String REQUEST_EXPIRED_ERROR = "Request has expired";
  private static final String INVALID_SIGNATURE_ERROR = "Invalid signature";

  private SlackRequestVerifierFilter slackRequestVerifierFilter;

  @Before
  public void setup() {
    this.slackRequestVerifierFilter =
      new SlackRequestVerifierFilter(REQUEST_EXPIRATION_TIME, SIGNING_SECRET);
  }

  @Test
  public void itFailsBecauseOfEmptyTimestampHeader() {
    ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
    when(requestContext.getHeaders()).thenReturn(getEmptyMap());

    assertThatThrownBy(() -> slackRequestVerifierFilter.filter(requestContext))
      .as("Filter fails when timestamp header is empty")
      .isInstanceOf(NullPointerException.class)
      .hasMessage(String.format(HEADER_EMPTY_ERROR_TEMPLATE, SLACK_TIMESTAMP_HEADER));
  }

  @Test
  public void itFailsBecauseOfEmptySignatureHeader() {
    ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
    when(requestContext.getHeaders()).thenReturn(getMapWithTimestampHeader());

    assertThatThrownBy(() -> slackRequestVerifierFilter.filter(requestContext))
      .as("Filter fails when signature header is empty")
      .isInstanceOf(NullPointerException.class)
      .hasMessage(String.format(HEADER_EMPTY_ERROR_TEMPLATE, SLACK_SIGNATURE_HEADER));
  }

  @Test
  public void itFailsBecauseOfMultipleTimestampHeaderValue() {
    ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
    when(requestContext.getHeaders())
      .thenReturn(getMapHeaders(getList(1L, 2L), getList("signature")));

    assertThatThrownBy(() -> slackRequestVerifierFilter.filter(requestContext))
      .as("Filter fails when there are more than one timestamp header present")
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage(
        String.format(EXPECTED_SINGLE_HEADER_ERROR_TEMPLATE, SLACK_TIMESTAMP_HEADER)
      );
  }

  @Test
  public void itFailsBecauseOfMultipleSignatureHeaderValue() {
    ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
    when(requestContext.getHeaders())
      .thenReturn(getMapHeaders(getList(1L), getList("signature", "second signature")));

    assertThatThrownBy(() -> slackRequestVerifierFilter.filter(requestContext))
      .as("Filter fails when there are more than one signature header present")
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage(
        String.format(EXPECTED_SINGLE_HEADER_ERROR_TEMPLATE, SLACK_SIGNATURE_HEADER)
      );
  }

  @Test
  public void itFailsWhileGettingResponseBody() {
    ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
    ByteArrayInputStream inputStream = mock(ByteArrayInputStream.class);

    when(requestContext.getHeaders())
      .thenReturn(getMapHeaders(getList(1L), getList("signature")));
    when(requestContext.getEntityStream()).thenReturn(inputStream);

    assertThatThrownBy(() -> slackRequestVerifierFilter.filter(requestContext))
      .as("Filter fails while getting response body")
      .isInstanceOf(RuntimeException.class)
      .hasMessage(String.format(GET_REQUEST_BODY_ERROR_TEMPLATE, requestContext));
  }

  @Test
  public void itFailsWhenRequestExpires() {
    ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
    InputStream inputStream = new ByteArrayInputStream(
      "request body".getBytes(StandardCharsets.UTF_8)
    );

    when(requestContext.getHeaders())
      .thenReturn(getMapHeaders(getList(1L), getList("signature")));
    when(requestContext.getEntityStream()).thenReturn(inputStream);

    assertThatThrownBy(() -> slackRequestVerifierFilter.filter(requestContext))
      .as("Filter fails while request has expired")
      .isInstanceOf(RuntimeException.class)
      .hasMessage(REQUEST_EXPIRED_ERROR);
  }

  @Test
  public void itFailsWhenSignatureInvalid() {
    ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
    InputStream inputStream = new ByteArrayInputStream(
      "request body".getBytes(StandardCharsets.UTF_8)
    );

    when(requestContext.getHeaders())
      .thenReturn(
        getMapHeaders(getList(System.currentTimeMillis()), getList("signature"))
      );
    when(requestContext.getEntityStream()).thenReturn(inputStream);

    assertThatThrownBy(() -> slackRequestVerifierFilter.filter(requestContext))
      .as("Filter fails when signature invalid")
      .isInstanceOf(RuntimeException.class)
      .hasMessage(INVALID_SIGNATURE_ERROR);
  }

  @Test
  public void itFilterSuccessfully() {
    ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
    InputStream inputStream = new ByteArrayInputStream(
      "request body".getBytes(StandardCharsets.UTF_8)
    );

    when(requestContext.getHeaders())
      .thenReturn(getMapHeaders(getList(System.currentTimeMillis()), getList(SIGNATURE)));
    when(requestContext.getEntityStream()).thenReturn(inputStream);

    assertThatThrownBy(() -> slackRequestVerifierFilter.filter(requestContext))
      .as("Filter fails when signature invalid")
      .isInstanceOf(RuntimeException.class)
      .hasMessage(INVALID_SIGNATURE_ERROR);
  }

  private MultivaluedMap<String, String> getEmptyMap() {
    return new MultivaluedHashMap<>();
  }

  private MultivaluedMap<String, String> getMapWithTimestampHeader() {
    MultivaluedMap<String, String> result = new MultivaluedHashMap<>();
    result.put(SLACK_TIMESTAMP_HEADER, getList("1"));
    return result;
  }

  private MultivaluedMap<String, String> getMapHeaders(
    List<Long> timestampHeaders,
    List<String> signatures
  ) {
    MultivaluedMap<String, String> result = new MultivaluedHashMap<>();
    result.put(
      SLACK_TIMESTAMP_HEADER,
      timestampHeaders
        .stream()
        .map(longValue -> Long.toString(longValue))
        .collect(Collectors.toList())
    );
    result.put(SLACK_SIGNATURE_HEADER, signatures);
    return result;
  }

  private <T> List<T> getList(T... data) {
    return Stream.of(data).collect(Collectors.toList());
  }
}

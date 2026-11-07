package com.hubspot.slack.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.hubspot.algebra.Result;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.methods.SlackMethods;
import com.hubspot.slack.client.models.response.SlackError;
import com.hubspot.slack.client.models.response.SlackErrorType;
import com.hubspot.slack.client.models.response.SlackResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;

public class SlackWebClientTest {

  @Test
  public void itProactivelyRateLimits() {
    SlackWebClient slackClient = (SlackWebClient) SlackClientFactory
      .defaultFactory()
      .create(
        SlackClientRuntimeConfig
          .builder()
          .setTokenSupplier(() -> "xoxp-test-token")
          .setRequestDebugger((requestId, method, request) -> {})
          .setSlackRateLimiter((slackToken, slackMethod) ->
            SlackWebClient.RATE_LIMIT_SENTINEL_VALUE
          )
          .build()
      );

    Result<SlackResponse, SlackError> result = slackClient
      .executeLoggedAs(
        SlackMethods.api_test,
        HttpRequest
          .newBuilder()
          .setUrl("api.slack.com/" + SlackMethods.api_test.getMethod())
          .build(),
        null
      )
      .join();

    assertThat(result.isErr()).isTrue();
    assertThat(result.unwrapErrOrElseThrow().getType())
      .isEqualTo(SlackErrorType.RATE_LIMITED);
    assertThat(result.unwrapErrOrElseThrow().getError())
      .isEqualTo(SlackErrorType.RATE_LIMITED.key());
  }

  @Test
  public void itIncludesTeamIdWhenListingUsers() {
    AtomicReference<HttpRequest> capturedRequest = new AtomicReference<>();
    SlackWebClient slackClient = (SlackWebClient) SlackClientFactory
      .defaultFactory()
      .create(
        SlackClientRuntimeConfig
          .builder()
          .setTokenSupplier(() -> "xoxp-test-token")
          .setRequestDebugger((requestId, method, request) -> capturedRequest.set(request)
          )
          .setSlackRateLimiter((slackToken, slackMethod) ->
            SlackWebClient.RATE_LIMIT_SENTINEL_VALUE
          )
          .build()
      );

    slackClient.listUsers("T012AB3C4").iterator().next().join();

    HttpRequest request = capturedRequest.get();
    String requestBody = new String(
      request.getBody(ObjectMapperUtils.mapper()),
      StandardCharsets.UTF_8
    );

    assertThat(request.getUrl().getPath()).endsWith("/users.list");
    assertThat(requestBody).contains("team_id=T012AB3C4");
  }
}

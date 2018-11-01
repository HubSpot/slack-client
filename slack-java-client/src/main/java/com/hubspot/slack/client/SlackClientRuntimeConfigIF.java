package com.hubspot.slack.client;

import java.util.Optional;
import java.util.function.Supplier;

import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

import com.hubspot.horizon.HttpConfig;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.interceptors.calls.SlackMethodAcceptor;
import com.hubspot.slack.client.interceptors.http.RequestDebugger;
import com.hubspot.slack.client.interceptors.http.ResponseDebugger;
import com.hubspot.slack.client.ratelimiting.ByMethodRateLimiter;
import com.hubspot.slack.client.ratelimiting.SlackRateLimiter;

@Immutable
@HubSpotStyle
public interface SlackClientRuntimeConfigIF {
  Supplier<String> getTokenSupplier();

  @Default
  default Supplier<String> getSlackApiBasePath() {
    return () -> "https://slack.com/api";
  }

  @Default
  default Supplier<Integer> getUsersListBatchSize() {
    return () -> 1000;
  }

  @Default
  default Supplier<Integer> getChannelsListBatchSize() {
    return () -> 1000;
  }

  @Default
  default Supplier<Integer> getChannelsHistoryMessageBatchSize() {
    return () -> 1000;
  }

  @Default
  default Supplier<Integer> getConversationsHistoryMessageBatchSize() {
    return () -> 1000;
  }

  Optional<SlackRateLimiter> getSlackRateLimiter();

  Optional<HttpConfig> getHttpConfig();

  Optional<SlackMethodAcceptor> getMethodFilter();

  Optional<RequestDebugger> getRequestDebugger();
  Optional<ResponseDebugger> getResponseDebugger();
}

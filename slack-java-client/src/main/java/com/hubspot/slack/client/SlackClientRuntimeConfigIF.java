package com.hubspot.slack.client;

import java.util.Optional;
import java.util.function.Supplier;

import org.immutables.value.Value.Immutable;

import com.hubspot.horizon.HttpConfig;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.interceptors.calls.SlackMethodAcceptor;
import com.hubspot.slack.client.interceptors.http.RequestDebugger;
import com.hubspot.slack.client.interceptors.http.ResponseDebugger;

@Immutable
@HubSpotStyle
public interface SlackClientRuntimeConfigIF {
  Supplier<String> getTokenSupplier();

  Supplier<String> getSlackApiBasePath();
  Supplier<Integer> getUsersListBatchSize();
  Supplier<Integer> getChannelsListBatchSize();
  Supplier<Integer> getChannelsHistoryMessageBatchSize();
  Supplier<Integer> getConversationsHistoryMessageBatchSize();

  Optional<HttpConfig> getHttpConfig();

  Optional<SlackMethodAcceptor> getMethodFilter();

  Optional<RequestDebugger> getRequestDebugger();
  Optional<ResponseDebugger> getResponseDebugger();
}

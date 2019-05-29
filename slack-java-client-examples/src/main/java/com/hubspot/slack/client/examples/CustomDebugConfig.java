package com.hubspot.slack.client.examples;

import com.google.common.base.Stopwatch;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.SlackClientFactory;
import com.hubspot.slack.client.SlackClientRuntimeConfig;
import com.hubspot.slack.client.interceptors.http.ResponseDebugger;
import com.hubspot.slack.client.methods.SlackMethod;
import com.hubspot.slack.client.methods.SlackMethods;

public class CustomDebugConfig {
  public static SlackClient getClient() {
    return SlackClientFactory.defaultFactory().build(get());
  }

  public static SlackClientRuntimeConfig get() {
    return SlackClientRuntimeConfig.builder()
        .setTokenSupplier(() -> "a fake token")
        .setRequestDebugger((requestId, method, request) -> {
          if (method == SlackMethods.chat_postEphemeral) {
            // do something special with ephemeral messages
          }
        })
        .setResponseDebugger(new ResponseDebugger() {
          @Override
          public void debug(long requestId, SlackMethod method, Stopwatch timer, HttpRequest request, HttpResponse response) {
            // basic debug on everything
          }

          @Override
          public void debugTransportException(long requestId, SlackMethod method, HttpRequest request, Throwable exception) {
            // something went wrong talking to slack
          }

          @Override
          public void debugSlackApiError(long requestId, SlackMethod method, HttpRequest request, HttpResponse failure) {
            // slack tells us we did something wrong (or they had a problem)
          }

          @Override
          public void debugProcessingFailure(long requestId, SlackMethod method, HttpRequest request, HttpResponse response, Throwable ex) {
            // problem in the client code itself resulted in failure
          }
        })
        .build();
  }
}

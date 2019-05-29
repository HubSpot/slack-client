package com.hubspot.slack.client.examples;

import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.SlackClientFactory;
import com.hubspot.slack.client.SlackClientRuntimeConfig;
import com.hubspot.slack.client.interceptors.calls.SlackMethodAcceptor;
import com.hubspot.slack.client.methods.MethodWriteMode;
import com.hubspot.slack.client.methods.SlackMethod;
import com.hubspot.slack.client.methods.interceptor.HasChannel;

public class FilteringRuntimeConfig {

  public static SlackClient getClient() {
    return SlackClientFactory.defaultFactory().build(get());
  }

  public static SlackClientRuntimeConfig get() {
    return SlackClientRuntimeConfig.builder()
        .setTokenSupplier(() -> "a fake token")
        .setMethodFilter(
            new SlackMethodAcceptor() {
              @Override
              public String getFailureExplanation(SlackMethod method, Object params) {
                return "Only allow WRITE methods to our special channel in QA!";
              }

              @Override
              public boolean test(SlackMethod slackMethod, Object o) {
                if (isQa() && slackMethod.getWriteMode() == MethodWriteMode.WRITE) {
                  return o instanceof HasChannel && ((HasChannel) o).getChannelId().equals("snazzy id");
                }

                return true;
              }
            })
        .build();
  }

  private static boolean isQa() {
    return true; // fake method, just tell us if this is the QA environment
  }
}

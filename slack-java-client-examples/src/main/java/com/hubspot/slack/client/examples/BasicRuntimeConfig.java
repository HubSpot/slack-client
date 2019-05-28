package com.hubspot.slack.client.examples;

import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.SlackClientFactory;
import com.hubspot.slack.client.SlackClientRuntimeConfig;

public class BasicRuntimeConfig {

  public static SlackClient getClient() {
    return SlackClientFactory.defaultFactory().create(get());
  }

  public static SlackClientRuntimeConfig get() {
    return SlackClientRuntimeConfig.builder()
        .setTokenSupplier(() -> "a fake token")
        .build();
  }
}

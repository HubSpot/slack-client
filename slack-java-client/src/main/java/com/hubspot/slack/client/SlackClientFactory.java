package com.hubspot.slack.client;

import com.hubspot.slack.client.http.NioHttpClientFactory;

public interface SlackClientFactory extends SlackWebClient.Factory {

  static SlackClientFactory defaultFactory() {
    return DefaultSlackClientFactory.defaultFactory();
  }

  SlackClient build(NioHttpClientFactory httpClientFactory, SlackClientRuntimeConfig config);

  static class DefaultSlackClientFactory implements SlackClientFactory {
    private static final SlackClientFactory INSTANCE = new DefaultSlackClientFactory();

    public static SlackClientFactory defaultFactory() {
      return INSTANCE;
    }

    @Override
    public SlackClient build(SlackClientRuntimeConfig config) {
      return build(NioHttpClientFactory.defaultFactory(), config);
    }

    @Override
    public SlackClient build(NioHttpClientFactory httpClientFactory,
                             SlackClientRuntimeConfig config) {
      return new SlackWebClient(httpClientFactory, config);
    }
  }
}

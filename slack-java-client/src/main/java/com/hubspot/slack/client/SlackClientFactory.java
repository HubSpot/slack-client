package com.hubspot.slack.client;

import com.hubspot.slack.client.http.NioHttpClientFactory;

public interface SlackClientFactory {

  static SlackClientFactory defaultFactory() {
    return DefaultSlackClientFactory.defaultFactory();
  }

  SlackClient create(SlackClientRuntimeConfig config);

  SlackClient create(NioHttpClientFactory httpClientFactory, SlackClientRuntimeConfig config);

  static class DefaultSlackClientFactory implements SlackClientFactory {
    private static final SlackClientFactory INSTANCE = new DefaultSlackClientFactory();

    public static SlackClientFactory defaultFactory() {
      return INSTANCE;
    }

    @Override
    public SlackClient create(SlackClientRuntimeConfig config) {
      return create(NioHttpClientFactory.defaultFactory(), config);
    }

    @Override
    public SlackClient create(NioHttpClientFactory httpClientFactory,
                              SlackClientRuntimeConfig config) {
      return new SlackWebClient(httpClientFactory, config);
    }
  }
}

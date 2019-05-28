package com.hubspot.slack.client.http;

import com.hubspot.horizon.AsyncHttpClient;

public interface NioHttpClientFactory {
  static NioHttpClientFactory defaultFactory() {
    return DefaultNioHttpClientFactory.defaultFactory();
  }

  NioHttpClient wrap(AsyncHttpClient asyncHttpClient);

  static class DefaultNioHttpClientFactory implements NioHttpClientFactory {
    private static final NioHttpClientFactory INSTANCE = new DefaultNioHttpClientFactory();

    public static NioHttpClientFactory defaultFactory() {
      return INSTANCE;
    }

    public NioHttpClient wrap(AsyncHttpClient asyncHttpClient) {
      return new NioHttpClient(asyncHttpClient);
    }
  }
}

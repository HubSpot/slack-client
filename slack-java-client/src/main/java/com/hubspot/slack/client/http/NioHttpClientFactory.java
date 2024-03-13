package com.hubspot.slack.client.http;

import com.hubspot.horizon.AsyncHttpClient;

public interface NioHttpClientFactory extends NioHttpClient.Factory {
  static NioHttpClientFactory defaultFactory() {
    return DefaultNioHttpClientFactory.defaultFactory();
  }

  static class DefaultNioHttpClientFactory implements NioHttpClientFactory {

    private static final NioHttpClientFactory INSTANCE =
      new DefaultNioHttpClientFactory();

    public static NioHttpClientFactory defaultFactory() {
      return INSTANCE;
    }

    @Override
    public NioHttpClient wrap(AsyncHttpClient asyncHttpClient) {
      return new NioHttpClient(asyncHttpClient);
    }
  }
}

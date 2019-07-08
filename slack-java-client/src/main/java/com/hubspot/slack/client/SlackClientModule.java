package com.hubspot.slack.client;

import com.google.inject.AbstractModule;
import com.hubspot.slack.client.http.NioHttpClient;
import com.hubspot.slack.client.http.NioHttpClientFactory;

/**
 * Use slack-java-client-guice module instead
 */
@Deprecated
public class SlackClientModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(NioHttpClientFactory.class).toInstance(NioHttpClientFactory.defaultFactory());
    bind(NioHttpClient.Factory.class).toInstance(NioHttpClientFactory.defaultFactory());

    bind(SlackClientFactory.class).toInstance(SlackClientFactory.defaultFactory());
    bind(SlackWebClient.Factory.class).toInstance(SlackClientFactory.defaultFactory());
  }

  @Override
  public boolean equals(Object o) {
    return o != null && getClass().equals(o.getClass());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}

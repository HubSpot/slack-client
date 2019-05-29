package com.hubspot.slack.client.guice;

import com.google.inject.AbstractModule;
import com.hubspot.slack.client.SlackClientFactory;
import com.hubspot.slack.client.SlackWebClient;
import com.hubspot.slack.client.http.NioHttpClient;
import com.hubspot.slack.client.http.NioHttpClientFactory;

public class SlackClientModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(SlackWebClient.Factory.class).toInstance(SlackClientFactory.defaultFactory());
    bind(SlackClientFactory.class).toInstance(SlackClientFactory.defaultFactory());

    bind(NioHttpClientFactory.class).toInstance(NioHttpClientFactory.defaultFactory());
    bind(NioHttpClient.Factory.class).toInstance(NioHttpClientFactory.defaultFactory());
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

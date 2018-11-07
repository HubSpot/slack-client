package com.hubspot.slack.client;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.hubspot.slack.client.http.NioHttpClient;

public class SlackClientModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new FactoryModuleBuilder().build(NioHttpClient.Factory.class));
    install(new FactoryModuleBuilder()
        .implement(SlackClient.class, SlackWebClient.class)
        .build(SlackWebClient.Factory.class));
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

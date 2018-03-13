package com.hubspot.slack.client;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.hubspot.horizon.HttpConfig;
import com.hubspot.horizon.ning.NingAsyncHttpClient;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;

public class SlackClientModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new FactoryModuleBuilder()
        .implement(SlackClient.class, SlackWebClient.class)
        .build(SlackWebClient.Factory.class));
  }

  @Slack
  @Provides
  @Singleton
  public NingAsyncHttpClient providesHttpClient() {
    return new NingAsyncHttpClient(
        HttpConfig.newBuilder()
            .setObjectMapper(ObjectMapperUtils.mapper())
            .build()
    );
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

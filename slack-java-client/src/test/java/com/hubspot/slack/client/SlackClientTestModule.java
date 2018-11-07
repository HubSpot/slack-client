package com.hubspot.slack.client;

import com.google.inject.AbstractModule;

public class SlackClientTestModule extends AbstractModule {
  @Override
  protected void configure() {
    binder().requireAtInjectOnConstructors();
    install(new SlackClientModule());
  }
}

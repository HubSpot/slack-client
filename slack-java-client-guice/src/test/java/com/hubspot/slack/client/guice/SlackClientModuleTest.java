package com.hubspot.slack.client.guice;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.hubspot.slack.client.SlackClientFactory;

public class SlackClientModuleTest {

  @Test
  public void itGuices() {
    Guice.createInjector(new StrictGuiceModule())
        .getInstance(SlackClientFactory.class);
  }

  private static class StrictGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
      binder().requireAtInjectOnConstructors();

      install(new SlackClientModule());
    }
  }

  @Test
  public void itCanHaveBothModulesInstalled() {
    Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        binder().requireAtInjectOnConstructors();

        install(new SlackClientModule());
        install(new com.hubspot.slack.client.SlackClientModule());
      }
    }).getInstance(SlackClientFactory.class);
  }
}

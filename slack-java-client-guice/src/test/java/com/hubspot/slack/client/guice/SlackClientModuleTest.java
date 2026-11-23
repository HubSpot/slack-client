package com.hubspot.slack.client.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hubspot.slack.client.SlackClientFactory;
import com.hubspot.slack.client.SlackWebClient;
import org.junit.Test;

public class SlackClientModuleTest {

  @Test
  public void itGuices() {
    Guice.createInjector(new StrictGuiceModule()).getInstance(SlackClientFactory.class);
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
    Injector injector = Guice.createInjector(
      new AbstractModule() {
        @Override
        protected void configure() {
          binder().requireAtInjectOnConstructors();

          install(new SlackClientModule());
        }
      }
    );

    injector.getInstance(SlackClientFactory.class);
    injector.getInstance(SlackWebClient.Factory.class);
  }
}

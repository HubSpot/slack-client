package com.hubspot.slack.client;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

public class SlackJavaClientModuleTest {

  @Test
  public void itGuices() {
    Injector injector = Guice.createInjector(Stage.PRODUCTION, new SlackClientModule());
    SlackWebClient.Factory clientFactory = injector.getInstance(SlackWebClient.Factory.class);
    SlackWebClient client = clientFactory.build(SlackClientRuntimeConfig.builder().setTokenSupplier(() -> "redacted").build());
  }
}

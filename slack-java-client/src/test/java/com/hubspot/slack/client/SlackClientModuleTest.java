package com.hubspot.slack.client;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Stage;

public class SlackClientModuleTest {

  @Test
  public void itGuices() {
    Guice.createInjector(Stage.TOOL, new SlackClientModule());
  }
}

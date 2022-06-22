package com.hubspot.slack.client.guice;

import com.google.inject.AbstractModule;
import com.hubspot.slack.client.request.verifier.SlackRequestVerifierModule;

public class SlackClientModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new com.hubspot.slack.client.SlackClientModule());
    install(new SlackRequestVerifierModule());
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

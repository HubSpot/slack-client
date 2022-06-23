package com.hubspot.slack.client.request.verifier;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class SlackRequestVerifierModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new FactoryModuleBuilder().build(SlackRequestVerifierFilter.Factory.class));
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

package com.hubspot.slack.client.models.views;

import org.immutables.value.Value;

public interface HomeTabViewPayloadBase extends ViewPayloadBase {
  String TYPE = "home";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }
}

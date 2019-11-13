package com.hubspot.slack.client.models.blocks;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface DividerIF extends Block {
  String TYPE = "divider";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }
}

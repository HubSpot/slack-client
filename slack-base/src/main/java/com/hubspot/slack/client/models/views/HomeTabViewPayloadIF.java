package com.hubspot.slack.client.models.views;

import java.util.List;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.Block;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface HomeTabViewPayloadIF extends ViewPayloadBase {
  String TYPE = "home";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Override
  @Value.Parameter
  List<Block> getBlocks();
}

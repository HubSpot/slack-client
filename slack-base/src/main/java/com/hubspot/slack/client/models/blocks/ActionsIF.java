package com.hubspot.slack.client.models.blocks;

import java.util.List;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.elements.BlockElement;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ActionsIF extends Block {
  String TYPE = "actions";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  List<BlockElement> getElements();
}

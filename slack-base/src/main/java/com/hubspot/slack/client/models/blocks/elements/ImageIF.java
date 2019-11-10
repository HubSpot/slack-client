package com.hubspot.slack.client.models.blocks.elements;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ImageIF extends BlockElement {
  String TYPE = "image";

  @Override
  @Value.Default
  default String getType() {
    return TYPE;
  }

  String getImageUrl();

  String getAltText();
}

package com.hubspot.slack.client.models.blocks;

import java.util.Optional;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.objects.Text;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ImageIF extends Block {
  String TYPE = "image";

  @Override
  @Value.Default
  default String getType() {
    return TYPE;
  }

  String getImageUrl();

  String getAltText();

  Optional<Text> getTitle();
}

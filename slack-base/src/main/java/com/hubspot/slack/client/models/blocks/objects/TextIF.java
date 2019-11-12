package com.hubspot.slack.client.models.blocks.objects;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface TextIF extends CompositionObject, ImageOrText {
  TextType getType();

  Optional<String> getText();

  @JsonProperty("emoji")
  Optional<Boolean> isEmoji();

  @JsonProperty("verbatim")
  Optional<Boolean> isVerbatim();
}

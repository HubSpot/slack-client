package com.hubspot.slack.client.models.blocks;

import java.util.Optional;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.elements.BlockElement;
import com.hubspot.slack.client.models.blocks.objects.Text;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface InputIF extends Block {
  String TYPE = "input";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter
  Text getLabel();

  @Value.Parameter
  BlockElement getElement();

  Optional<Text> getHint();

  @JsonProperty("optional")
  Optional<Boolean> isOptional();
}

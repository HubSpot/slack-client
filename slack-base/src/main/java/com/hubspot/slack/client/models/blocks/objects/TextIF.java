package com.hubspot.slack.client.models.blocks.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface TextIF extends CompositionObject, ImageBlockOrText {
  @Value.Parameter
  TextType getType();

  @Value.Parameter
  String getText();

  @JsonProperty("emoji")
  Optional<Boolean> isEmoji();

  @JsonProperty("verbatim")
  Optional<Boolean> isVerbatim();
}

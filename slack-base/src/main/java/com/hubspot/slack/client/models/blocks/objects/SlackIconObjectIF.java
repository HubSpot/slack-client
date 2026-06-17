package com.hubspot.slack.client.models.blocks.objects;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface SlackIconObjectIF extends CompositionObject {
  String TYPE = "icon";

  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter
  String getName();
}

package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface MessageContainerIF extends Container {
  String getMessageTs();

  String getChannelId();

  @JsonProperty("is_ephemeral")
  boolean isEphemeral();
}

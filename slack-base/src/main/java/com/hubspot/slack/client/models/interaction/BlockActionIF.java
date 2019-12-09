package com.hubspot.slack.client.models.interaction;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.LiteMessage;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface BlockActionIF extends SlackInteractiveCallback {
  String getTriggerId();
  String getResponseUrl();
  LiteMessage getMessage();
}

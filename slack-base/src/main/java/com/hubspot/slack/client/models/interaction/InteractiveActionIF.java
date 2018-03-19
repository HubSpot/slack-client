package com.hubspot.slack.client.models.interaction;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.LiteMessage;
import com.hubspot.slack.client.models.actions.Action;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface InteractiveActionIF extends SlackInteractiveCallback {
  List<Action> getActions();

  String getMessageTs();

  int getAttachmentId();

  @JsonProperty("is_app_unfurl")
  boolean isAppUnfurl();

  Optional<LiteMessage> getOriginalMessage();
  String getResponseUrl();
  String getTriggerId();
}

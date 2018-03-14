package com.hubspot.slack.client.models.interaction;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.styles.HubSpotStyle;
import com.hubspot.slack.client.models.SlackChannel;
import com.hubspot.slack.client.models.teams.SlackTeam;
import com.hubspot.slack.client.models.users.SlackUserLite;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface InteractiveLoadOptionsRequestIF {
  String getName();
  String getValue();
  String getCallbackId();
  SlackTeam getTeam();
  SlackChannel getChannel();
  SlackUserLite getUser();

  String getActionTs();
  String getMessageTs();
  String getAttachmentId();
  String getToken();
}

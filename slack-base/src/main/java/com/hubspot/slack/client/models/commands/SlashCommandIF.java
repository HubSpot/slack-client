package com.hubspot.slack.client.models.commands;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface SlashCommandIF {
  String getToken();
  String getTeamId();
  String getTeamDomain();
  Optional<String> getEnterpriseId();
  Optional<String> getEnterpriseName();
  String getChannelId();
  String getChannelName();
  String getUserId();
  @Deprecated
  String getUserName();
  String getCommand();
  String getText();
  String getResponseUrl();
  String getTriggerId();
}

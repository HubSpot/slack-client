package com.hubspot.slack.client.models.response.auth;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface AuthTestResponseIF extends SlackResponse {
  String getUrl();
  String getTeam();
  String getTeamId();
  Optional<String> getUser();
  Optional<String> getUserId();
}

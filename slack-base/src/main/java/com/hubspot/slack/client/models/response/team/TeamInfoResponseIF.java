package com.hubspot.slack.client.models.response.team;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;
import com.hubspot.slack.client.models.teams.SlackTeam;

@Immutable
@HubSpotStyle
public interface TeamInfoResponseIF extends SlackResponse {
  @JsonProperty("team")
  SlackTeam getSlackTeam();
}

package com.hubspot.slack.client.models.response.team;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;
import com.hubspot.slack.client.models.teams.SlackTeam;

@Immutable
@HubSpotStyle
public interface TeamInfoResponseIF extends SlackResponse {
  SlackTeam getSlackTeam();
}

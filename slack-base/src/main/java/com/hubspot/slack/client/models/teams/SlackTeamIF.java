package com.hubspot.slack.client.models.teams;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface SlackTeamIF {
  String getId();
  String getDomain();
}

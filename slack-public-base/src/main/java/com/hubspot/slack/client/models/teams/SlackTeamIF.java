package com.hubspot.slack.client.models.teams;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.styles.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface SlackTeamIF {
  String getId();
  String getDomain();
}

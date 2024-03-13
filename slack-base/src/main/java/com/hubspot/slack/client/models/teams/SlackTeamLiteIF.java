package com.hubspot.slack.client.models.teams;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface SlackTeamLiteIF {
  String getId();
  String getName();
}

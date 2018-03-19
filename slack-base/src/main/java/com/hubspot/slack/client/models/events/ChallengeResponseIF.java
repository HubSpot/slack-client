package com.hubspot.slack.client.models.events;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface ChallengeResponseIF {
  String getChallenge();
}

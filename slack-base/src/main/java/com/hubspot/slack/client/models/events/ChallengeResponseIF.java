package com.hubspot.slack.client.models.events;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface ChallengeResponseIF {
  String getChallenge();
}

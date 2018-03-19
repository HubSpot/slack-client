package com.hubspot.slack.client.models.response;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface SlackErrorResponseIF extends SlackResponse {
  SlackError getError();
}

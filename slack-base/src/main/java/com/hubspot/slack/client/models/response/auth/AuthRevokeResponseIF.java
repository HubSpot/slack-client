package com.hubspot.slack.client.models.response.auth;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;

@Immutable
@HubSpotStyle
public interface AuthRevokeResponseIF extends SlackResponse {
  boolean isRevoked();
}

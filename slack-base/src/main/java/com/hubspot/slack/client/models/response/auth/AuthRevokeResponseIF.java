package com.hubspot.slack.client.models.response.auth;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface AuthRevokeResponseIF extends SlackResponse {
  boolean isRevoked();
}

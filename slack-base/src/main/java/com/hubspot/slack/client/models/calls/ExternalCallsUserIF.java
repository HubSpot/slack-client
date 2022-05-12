package com.hubspot.slack.client.models.calls;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
public interface ExternalCallsUserIF extends SlackInternalOrExternalUser {
  String getExternalId();
  String getDisplayName();
  String getAvatarUrl();
}

package com.hubspot.slack.client.models.calls;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
public interface SlackCallsUserIF extends SlackInternalOrExternalUser {
  @Value.Parameter(order = 1)
  String getSlackId();
}

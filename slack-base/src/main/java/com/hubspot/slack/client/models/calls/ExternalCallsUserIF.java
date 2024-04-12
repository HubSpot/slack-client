package com.hubspot.slack.client.models.calls;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface ExternalCallsUserIF extends SlackInternalOrExternalUser {
  String getExternalId();

  String getDisplayName();

  String getAvatarUrl();
}

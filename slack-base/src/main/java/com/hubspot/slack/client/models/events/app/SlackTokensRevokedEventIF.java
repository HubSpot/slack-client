package com.hubspot.slack.client.models.events.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.events.SlackEvent;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackTokensRevokedEvent.class)
public interface SlackTokensRevokedEventIF extends SlackEvent {
  @JsonProperty
  TokensRevoked getTokens();

  //Tokens revoked events do not have a ts, so we manually set it as null
  @Override
  default String getTs() {
    return null;
  }
}

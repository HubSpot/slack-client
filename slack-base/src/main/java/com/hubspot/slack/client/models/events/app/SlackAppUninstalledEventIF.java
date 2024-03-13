package com.hubspot.slack.client.models.events.app;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.events.SlackEvent;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackAppUninstalledEvent.class)
public interface SlackAppUninstalledEventIF extends SlackEvent {
  //App uninstall events do not have a ts, so we manually set it as null
  @Override
  default String getTs() {
    return null;
  }
}

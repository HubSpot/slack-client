package com.hubspot.slack.client.models.events.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.events.SlackEvent;
import org.immutables.value.Value;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackGroupOpenEvent.class)
@Value.Immutable
@HubSpotStyle
public interface SlackGroupOpenEventIF extends SlackEvent {
  @JsonProperty("channel")
  String getChannelId();

  @JsonProperty("user")
  String getUserId();

  //Group open events do not have a ts, so we manually set it as null
  @Override
  default String getTs() {
    return null;
  }
}

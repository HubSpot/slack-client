package com.hubspot.slack.client.models.events.channel;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.SlackChannel;
import com.hubspot.slack.client.models.events.SlackEvent;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackChannelCreatedEvent.class)
public interface SlackChannelCreatedEventIF extends SlackEvent {
  SlackChannel getChannel();

  @JsonProperty("channel.created")
  long getChannelCreatedTime();

  @JsonProperty("channel.creator")
  String getCreaterUserId();
}

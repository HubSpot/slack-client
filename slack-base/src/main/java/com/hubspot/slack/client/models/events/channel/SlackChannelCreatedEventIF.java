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
  //Channel.created and channel.creator do not exist on the SlackChannel object and consequently aren't parsed right now. If we need these in the future
  //we can define a wrapper around SlackChannel containing those two fields.

  SlackChannel getChannel();

  //Channel created events do not have a ts, so we manually set it as null
  @Override
  default String getTs() {
    return null;
  }
}

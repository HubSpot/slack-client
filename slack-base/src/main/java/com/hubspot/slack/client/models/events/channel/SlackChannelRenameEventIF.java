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
@JsonDeserialize(as = SlackChannelRenameEvent.class)
public interface SlackChannelRenameEventIF extends SlackEvent {
  //Channel.created does not exist on the SlackChannel object and consequently isn't parsed right now. If we need this in the future
  //we can define a wrapper around SlackChannel containing that field

  SlackChannel getChannel();
}

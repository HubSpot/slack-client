package com.hubspot.slack.client.models.events.channel;

import com.hubspot.slack.client.models.events.conversation.SlackConversationEventWithChannel;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackChannelRenameEvent.class)
public interface SlackChannelRenameEventIF extends SlackConversationEventWithChannel {
  //Channel.created does not exist on the SlackChannel object and consequently isn't parsed right now. If we need this in the future
  //we can define a wrapper around SlackChannel containing that field
}

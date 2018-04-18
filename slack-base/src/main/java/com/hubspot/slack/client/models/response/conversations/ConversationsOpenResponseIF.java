package com.hubspot.slack.client.models.response.conversations;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.conversations.Conversation;
import com.hubspot.slack.client.models.response.SlackResponse;

@Immutable
@HubSpotStyle
public interface ConversationsOpenResponseIF extends SlackResponse {
  @JsonProperty("channel")  // this will actually be either a channel, group, or im depending on what was requested
  Conversation getConversation();
}

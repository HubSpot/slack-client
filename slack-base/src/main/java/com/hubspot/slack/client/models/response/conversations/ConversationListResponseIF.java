package com.hubspot.slack.client.models.response.conversations;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.conversations.Conversation;
import com.hubspot.slack.client.models.response.ResponseMetadata;
import com.hubspot.slack.client.models.response.SlackResponse;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ConversationListResponseIF extends SlackResponse {
  @JsonProperty("channels") // just. wow.
  List<Conversation> getConversations();

  Optional<ResponseMetadata> getResponseMetadata();
}

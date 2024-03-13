package com.hubspot.slack.client.models.response.conversations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.LiteMessage;
import com.hubspot.slack.client.models.response.SlackResponse;
import java.util.List;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ConversationsHistoryResponseIF extends SlackResponse {
  List<LiteMessage> getMessages();

  @JsonProperty("has_more")
  boolean hasMore();

  int getPinCount();
}

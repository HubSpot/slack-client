package com.hubspot.slack.client.models.response.chat;

import java.util.Set;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.ScheduledMessage;
import com.hubspot.slack.client.models.response.SlackResponse;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ChatScheduledMessagesListResponseIF extends SlackResponse {
  Set<ScheduledMessage> getScheduledMessages();
}

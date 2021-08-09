package com.hubspot.slack.client.models.response.chat;

import java.util.Map;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ChatScheduleMessageResponseIF extends SlackResponse {
  String getChannel();
  String getScheduledMessageId();
  String getPostAt();
  Map<String, Object> getMessage();
}

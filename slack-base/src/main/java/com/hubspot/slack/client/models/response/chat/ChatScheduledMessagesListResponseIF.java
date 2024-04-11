package com.hubspot.slack.client.models.response.chat;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.ScheduledMessage;
import com.hubspot.slack.client.models.response.SlackResponse;
import java.util.Set;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ChatScheduledMessagesListResponseIF extends SlackResponse {
  Set<ScheduledMessage> getScheduledMessages();
}

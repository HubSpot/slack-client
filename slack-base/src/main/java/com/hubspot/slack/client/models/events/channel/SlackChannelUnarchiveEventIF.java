package com.hubspot.slack.client.models.events.channel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.events.conversation.SlackConversationEventCore;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackChannelUnarchiveEvent.class)
public interface SlackChannelUnarchiveEventIF extends SlackConversationEventCore {
  @JsonProperty("user")
  String getUnarchivedByUserId();
}

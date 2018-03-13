package com.hubspot.slack.client.models.events;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.styles.HubSpotStyle;
import com.hubspot.slack.client.models.events.util.SlackReplyMessage;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface SlackEventMessageRepliedIF extends SlackEventMessageBase {
  SlackReplyMessage getMessage();
}

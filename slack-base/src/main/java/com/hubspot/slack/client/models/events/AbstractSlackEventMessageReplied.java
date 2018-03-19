package com.hubspot.slack.client.models.events;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.events.util.SlackReplyMessage;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public abstract class AbstractSlackEventMessageReplied extends SlackEventMessageBase {
  @JsonProperty("channel")
  public abstract String getChannelId();

  public abstract SlackReplyMessage getMessage();
}

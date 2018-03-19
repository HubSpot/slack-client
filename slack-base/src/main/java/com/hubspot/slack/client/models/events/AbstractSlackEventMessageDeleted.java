package com.hubspot.slack.client.models.events;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.LiteMessage;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackEventMessageDeleted.class)
public abstract class AbstractSlackEventMessageDeleted extends SlackEventMessageBase {
  @JsonProperty("channel")
  public abstract String getChannelId();

  public abstract Optional<LiteMessage> getPreviousMessage();

  public abstract boolean isHidden();
  public abstract String getDeletedTs();
  public abstract String getEventTs();
}

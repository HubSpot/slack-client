package com.hubspot.slack.client.models.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.Attachment;
import com.hubspot.slack.client.models.blocks.Block;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackEventBotMessage.class)
public abstract class AbstractSlackEventBotMessage extends SlackEventMessageBase {

  @JsonProperty("channel")
  public abstract String getChannelId();

  public abstract Optional<String> getThreadTs();

  public abstract Optional<String> getText();

  public abstract List<Attachment> getAttachments();

  public abstract String getBotId();

  public abstract List<Block> getBlocks();
}

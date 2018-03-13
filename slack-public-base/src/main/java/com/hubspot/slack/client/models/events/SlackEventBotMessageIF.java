package com.hubspot.slack.client.models.events;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.styles.HubSpotStyle;
import com.hubspot.slack.client.models.Attachment;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackEventBotMessage.class)
public interface SlackEventBotMessageIF extends SlackEventMessageBase {
  Optional<String> getText();
  List<Attachment> getAttachments();
  String getBotId();
}

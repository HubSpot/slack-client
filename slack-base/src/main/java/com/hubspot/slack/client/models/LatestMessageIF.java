package com.hubspot.slack.client.models;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.reactions.Reaction;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface LatestMessageIF {
  String getText();
  List<Attachment> getAttachments();

  String getType();
  Optional<String> getSubtype();

  @JsonProperty("user")
  Optional<String> getUserId();
  Optional<String> getUsername();
  Optional<String> getParentUserId();
  Optional<String> getBotId();

  @JsonProperty("ts")
  String getTimestamp();
  @JsonProperty("thread_ts")
  Optional<String> getThreadTimestamp();
  @JsonProperty("client_msg_id")
  Optional<String> getClientMessageId();

  List<Reaction> getReactions();
  Optional<EditInfo> getEdited();
}

package com.hubspot.slack.client.models;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.Block;
import com.hubspot.slack.client.models.files.SlackFile;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface LiteMessageIF {

  String getType();

  Optional<String> getSubtype();

  Optional<String> getUser();

  Optional<String> getBotId();

  Optional<String> getUsername();

  String getText();

  List<Attachment> getAttachments();

  List<SlackFile> getFiles();

  List<Block> getBlocks();

  List<Reaction> getReactions();

  @JsonProperty("ts")
  String getTimestamp();

  @JsonProperty("thread_ts")
  Optional<String> getThreadTimestamp();

  Optional<Integer> getReplyCount();

  @JsonProperty("reply_users")
  Optional<List<String>> getReplyUserIds();

  Optional<Integer> getReplyUsersCount();

  @JsonProperty("latest_reply")
  Optional<String> getLatestReplyTimestamp();
}

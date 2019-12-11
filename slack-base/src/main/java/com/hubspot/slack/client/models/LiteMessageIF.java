package com.hubspot.slack.client.models;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  Logger LOG = LoggerFactory.getLogger(LiteMessage.class);

  String getType();

  Optional<String> getSubtype();

  Optional<String> getUser();

  Optional<String> getBotId();

  Optional<String> getUsername();

  String getText();

  List<Attachment> getAttachments();

  List<SlackFile> getFiles();

  List<Block> getBlocks();

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

  /**
   * @deprecated use {@link #getReplyUserIds()} or {@link #getLatestReplyTimestamp()}
   * These can be used to find the user ids and the last reply timestamp.
   * Used to return a list of `ReplySkeleton`
   */
  @Deprecated
  @Default
  default List<ReplySkeleton> getReplies() {
    LOG.error("Method getReplies() is now deprecated. Slack will stop supporting completely on Oct 18th 2019");
    return Collections.emptyList();
  }
}

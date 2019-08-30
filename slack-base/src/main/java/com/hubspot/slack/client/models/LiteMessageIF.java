package com.hubspot.slack.client.models;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
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

  @JsonProperty("ts")
  String getTimestamp();

  @JsonProperty("thread_ts")
  Optional<String> getThreadTimestamp();

  Optional<Integer> getReplyCount();

  Optional<List<String>> getReplyUsers();

  Optional<Integer> getResplyUsersCount();

  @JsonProperty("latest_reply")
  Optional<String> getLatestReplyTimestamp();

  /**
   * @deprecated use {@link #getReplyUsers()} or {@link #getLatestReplyTimestamp()}
   * These can be used to find the user ids and the last reply timestamp.
   * Used to return a list of `ReplySkeleton`
   */
  @Deprecated
  @Derived
  @JsonIgnore
  default List getReplies() {
    LOG.error("Method getReplies() is now deprecated. Slack will stop supporting completely on Oct 18th 2019");
    return Collections.emptyList();
  }
}

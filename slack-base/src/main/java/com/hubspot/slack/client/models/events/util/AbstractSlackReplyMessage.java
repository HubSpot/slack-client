package com.hubspot.slack.client.models.events.util;

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
import com.hubspot.slack.client.methods.interceptor.HasUser;
import com.hubspot.slack.client.models.events.SlackEventMessageBase;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public abstract class AbstractSlackReplyMessage extends SlackEventMessageBase implements HasUser {

  Logger LOG = LoggerFactory.getLogger(SlackReplyMessage.class);

  public abstract String getThreadTs();

  @Override
  @JsonProperty("user")
  public abstract String getUserId();

  @Override
  @JsonProperty("channel")
  public abstract String getChannelId();

  @Default
  public int getReplyCount() {
    return 0;
  }

  @JsonProperty("reply_users")
  public abstract Optional<List<String>> getReplyUserIds();

  public abstract Optional<Integer> getReplyUsersCount();

  @JsonProperty("latest_reply")
  public abstract Optional<String> getLatestReplyTimestamp();

  /**
   * @deprecated use {@link #getReplyUserIds()} or {@link #getLatestReplyTimestamp()}
   * These can be used to find the user ids and the last reply timestamp.
   * Used to return a list of `Reply`
   */
  @Deprecated
  @Default
  public List<Reply> getReplies() {
    LOG.error("Method getReplies() is now deprecated. Slack will stop supporting completely on Oct 18th 2019");
    return Collections.emptyList();
  }
}

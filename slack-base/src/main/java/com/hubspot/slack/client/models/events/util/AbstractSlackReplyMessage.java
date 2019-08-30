package com.hubspot.slack.client.models.events.util;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Default;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

  public abstract Optional<List<String>> getReplyUsers();

  public abstract Optional<Integer> getResplyUsersCount();

  @JsonProperty("latest_reply")
  public abstract Optional<String> getLatestReplyTimestamp();

  /**
   * @deprecated use {@link #getReplyUsers()} or {@link #getLatestReplyTimestamp()}
   * These can be used to find the user ids and the last reply timestamp.
   * Used to return a list of `ReplySkeleton`
   */
  @Deprecated
  @Derived
  @JsonIgnore
  public List getReplies() {
    return Collections.emptyList();
  }
}

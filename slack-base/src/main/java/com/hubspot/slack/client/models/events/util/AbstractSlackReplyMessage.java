package com.hubspot.slack.client.models.events.util;

import java.util.List;

import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

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

  public abstract List<Reply> getReplies();
}

package com.hubspot.slack.client.models.events.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasUser;
import com.hubspot.slack.client.models.events.SlackEventMessageBase;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public abstract class AbstractSlackReplyMessage
  extends SlackEventMessageBase
  implements HasUser {

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
}

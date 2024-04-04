package com.hubspot.slack.client.models.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.slack.client.methods.interceptor.HasUser;
import java.util.Optional;

public abstract class SlackEventMessageExtendedBase
  extends SlackEventMessageBase
  implements HasUser {

  @JsonProperty("channel")
  public abstract String getChannelId();

  public abstract Optional<String> getThreadTs();

  public abstract Optional<String> getBotId();

  @Override
  @JsonProperty("user")
  public abstract String getUserId();

  public abstract String getText();
}

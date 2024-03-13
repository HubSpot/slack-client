package com.hubspot.slack.client.models.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasUser;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackEventMessageIm.class)
public abstract class AbstractSlackEventMessageIm
  extends SlackEventMessageBase
  implements HasUser {

  @JsonProperty("channel")
  public abstract String getChannelId();

  public abstract Optional<String> getThreadTs();

  public abstract String getText();

  @Override
  @JsonProperty("user")
  public abstract String getUserId();
}

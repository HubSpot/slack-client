package com.hubspot.slack.client.models.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasUser;
import com.hubspot.slack.client.models.Attachment;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackEventMessage.class)
public abstract class AbstractSlackEventMessage
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

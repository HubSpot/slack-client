package com.hubspot.slack.client.methods.params.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasChannel;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public abstract class AbstractChatDeleteParams implements HasChannel {

  @Override
  @JsonProperty("channel")
  public abstract String getChannelId();

  @JsonProperty("ts")
  public abstract String getMessageToDeleteTs();

  @Default
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("as_user")
  public boolean getAsUser() {
    return false;
  }
}

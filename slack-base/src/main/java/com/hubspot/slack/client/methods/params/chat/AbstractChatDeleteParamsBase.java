package com.hubspot.slack.client.methods.params.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasChannel;

@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public abstract class AbstractChatDeleteParamsBase implements HasChannel {
  @Override
  @JsonProperty("channel")
  public abstract String getChannelId();

  @JsonProperty("ts")
  public abstract String getMessageToDeleteTs();
}

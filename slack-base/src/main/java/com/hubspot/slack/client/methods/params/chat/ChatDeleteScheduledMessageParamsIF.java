package com.hubspot.slack.client.methods.params.chat;

import org.immutables.value.Value.Immutable;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasChannel;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ChatDeleteScheduledMessageParamsIF extends HasChannel {
  @Override
  @JsonProperty("channel")
  String getChannelId();

  String getScheduledMessageId();

  @JsonProperty("as_user")
  default boolean getAsUser() {
    return true;
  }
}

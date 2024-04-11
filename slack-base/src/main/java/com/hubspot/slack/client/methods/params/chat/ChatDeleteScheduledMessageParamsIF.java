package com.hubspot.slack.client.methods.params.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasChannel;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ChatDeleteScheduledMessageParamsIF extends HasChannel {
  @Override
  @JsonProperty("channel")
  String getChannelId();

  String getScheduledMessageId();

  default boolean getAsUser() {
    return true;
  }
}

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
public interface ChatGetPermalinkParamsIF extends HasChannel {
  @JsonProperty("channel")
  String getChannelId();

  String getMessageTs();
}

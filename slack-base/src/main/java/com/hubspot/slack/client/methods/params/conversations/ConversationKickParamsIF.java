package com.hubspot.slack.client.methods.params.conversations;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasChannel;

@Value.Immutable
@HubSpotStyle
public interface ConversationKickParamsIF extends HasChannel {
  @Override
  @JsonProperty("channel")
  String getChannelId();

  @JsonProperty("user")
  String getUserId();
}

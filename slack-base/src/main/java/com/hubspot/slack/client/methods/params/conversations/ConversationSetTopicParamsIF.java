package com.hubspot.slack.client.methods.params.conversations;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface ConversationSetTopicParamsIF {

  String getTopic();

  @JsonProperty("channel")
  String getChannelId();
}

package com.hubspot.slack.client.methods.params.conversations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface ConversationArchiveParamsIF extends ConversationArchiveBaseParams {
  @JsonProperty("channel")
  String getChannelId();
}

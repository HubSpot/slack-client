package com.hubspot.slack.client.methods.params.conversations;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ConversationsInfoParamsIF {
  @JsonProperty("channel")  // could be a channel ID, group ID, IM ID
  String getConversationId();
  Optional<Boolean> getIncludeLocale();
  Optional<Boolean> getIncludeNumMembers();
}

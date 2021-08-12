package com.hubspot.slack.client.methods.params.chat;

import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ChatScheduledMessagesListParamsIF {
  String EMPTY_STRING = "";
  int LIMIT = 100;

  @Default
  @JsonProperty("channel")
  default String getChannelId() {
    return EMPTY_STRING;
  }

  @Default
  default String getCursor() {
    return EMPTY_STRING;
  }

  @Default
  @JsonProperty("latest")
  default String getNewestTimestamp() {
    return EMPTY_STRING;
  }

  @Default
  @JsonProperty("oldest")
  default String getOldestTimestamp() {
    return EMPTY_STRING;
  }

  @Default
  default String getTeamId() {
    return EMPTY_STRING;
  }

  @Default
  default Integer getLimit() {
    return LIMIT;
  }
}

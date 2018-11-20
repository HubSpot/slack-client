package com.hubspot.slack.client.models;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface EditInfoIF {
  @JsonProperty("user")
  String getUserId();
  @JsonProperty("ts")
  String getTimestamp();
}

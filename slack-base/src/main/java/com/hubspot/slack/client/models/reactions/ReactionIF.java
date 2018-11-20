package com.hubspot.slack.client.models.reactions;

import java.util.List;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface ReactionIF {
  String getName();
  int getCount();
  @JsonProperty("users")
  List<String> getUserIds();

}

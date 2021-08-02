package com.hubspot.slack.client.models;

import java.util.List;

import org.immutables.value.Value.Immutable;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ReactionIF {
  String getName();

  Integer getCount();

  List<String> getUser();
}

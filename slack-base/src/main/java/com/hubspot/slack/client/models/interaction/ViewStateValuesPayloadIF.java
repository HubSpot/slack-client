package com.hubspot.slack.client.models.interaction;

import java.util.Map;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ViewStateValuesPayloadIF  {

  @JsonProperty("values")
  Map<String, Map<String, ViewStateValuesBlock>> getBlockIdToActionIdToValues();

}

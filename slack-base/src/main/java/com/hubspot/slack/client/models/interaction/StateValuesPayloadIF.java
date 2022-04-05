package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.interaction.views.ViewInput;
import java.util.Map;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface StateValuesPayloadIF {
  @JsonProperty("values")
  Map<String, Map<String, ViewInput>> getBlockIdToActionIdToValues();
}

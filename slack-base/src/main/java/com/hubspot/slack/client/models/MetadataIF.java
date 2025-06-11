package com.hubspot.slack.client.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Map;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface MetadataIF {
  @Parameter(order = 0)
  String getEventType();

  @Parameter(order = 1)
  Map<String, Object> getEventPayload();
}

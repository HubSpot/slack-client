package com.hubspot.slack.client.methods.params.stream;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Map;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface StreamMetadataIF {
  String getEventType();

  Map<String, Object> getEventPayload();
}

package com.hubspot.slack.client.models.files;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackFileTombstone.class)
public interface SlackFileTombstoneIF extends SlackFileError {
  @Override
  @Value.Default
  default String getFileAccess() {
    return "unknown";
  }
}

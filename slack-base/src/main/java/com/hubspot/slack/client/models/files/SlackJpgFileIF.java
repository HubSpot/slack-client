package com.hubspot.slack.client.models.files;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackJpgFile.class)
public interface SlackJpgFileIF extends SlackImageFile {
  @Value.Default
  @Override
  default SlackFileType getFiletype() {
    return SlackFileType.JPG;
  }
}

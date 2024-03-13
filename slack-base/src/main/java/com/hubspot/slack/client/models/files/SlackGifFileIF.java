package com.hubspot.slack.client.models.files;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackGifFile.class)
public interface SlackGifFileIF extends SlackImageFile {
  @Default
  @Override
  default SlackFileType getFiletype() {
    return SlackFileType.GIF;
  }
}

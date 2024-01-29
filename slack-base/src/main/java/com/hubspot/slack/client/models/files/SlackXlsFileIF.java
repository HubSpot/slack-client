package com.hubspot.slack.client.models.files;

import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackXlsFile.class)
public interface SlackXlsFileIF extends SlackFile {
  @Default
  @Override
  default SlackFileType getFiletype() {
    return SlackFileType.XLS;
  }
}

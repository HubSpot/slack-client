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
@JsonDeserialize(as = SlackXlsxFile.class)
public interface SlackXlsxFileIF extends SlackFile {
  @Default
  @Override
  default SlackFileType getFiletype() {
    return SlackFileType.XLSX;
  }
}

package com.hubspot.slack.client.methods.params.files;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface GetUploadURLExternalParamsIF {
  String getFilename();
  Long getLength();
  Optional<String> getAltText();
  Optional<String> getSnippetType();
}

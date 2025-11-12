package com.hubspot.slack.client.methods.params.chat.workobject.entity.fullsizepreview;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface FullSizePreviewIF {
  boolean isSupported();

  Optional<String> getPreviewUrl();

  Optional<String> getMimeType();
  Optional<FullSizePreviewError> getError();
}

package com.hubspot.slack.client.methods.params.chat.workobject.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.params.chat.workobject.entity.fullsizepreview.FullSizePreview;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface EntityPayloadAttributesIF {
  EntityPayloadAttributeTitle getTitle();
  Optional<String> getDisplayId();

  Optional<String> getDisplayType();
  Optional<String> getProductName();
  Optional<Icon> getProductIcon();
  Optional<String> getLocale();
  Optional<FullSizePreview> getFullSizePreview();
}

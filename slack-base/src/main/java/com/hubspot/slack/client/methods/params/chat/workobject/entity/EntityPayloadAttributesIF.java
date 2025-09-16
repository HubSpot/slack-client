package com.hubspot.slack.client.methods.params.chat.workobject.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.params.chat.workobject.serializers.DisplayTypeSerializer;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface EntityPayloadAttributesIF {
  EntityPayloadAttributeTitle getTitle();
  Optional<String> getDisplayId();

  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  @JsonSerialize(using = DisplayTypeSerializer.class)
  Optional<DisplayType> getDisplayType();

  Optional<String> getProductName();
  Optional<ProductIcon> getProductIcon();
  Optional<String> getLocale();
}

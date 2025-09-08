package com.hubspot.slack.client.methods.params.chat.workobject.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.params.chat.workobject.ProductIcon;
import com.hubspot.slack.client.methods.params.chat.workobject.serializers.DisplayTypeSerializer;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
public interface EntityPayloadAttributesIF {
  String getTitle();
  Optional<String> getDisplayId();

  @JsonSerialize(using = DisplayTypeSerializer.class)
  Optional<DisplayType> getDisplayType();

  Optional<String> getProductName();
  Optional<ProductIcon> getProductIcon();
  Optional<String> getLocale();
}

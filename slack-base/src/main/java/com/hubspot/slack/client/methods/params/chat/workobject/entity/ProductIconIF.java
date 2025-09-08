package com.hubspot.slack.client.methods.params.chat.workobject.entity;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
public interface ProductIconIF {
  String getUrl();
  String getAltText();
}

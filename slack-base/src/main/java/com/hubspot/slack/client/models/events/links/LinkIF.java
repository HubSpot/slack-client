package com.hubspot.slack.client.models.events.links;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface LinkIF {
  String getDomain();

  String getUrl();
}

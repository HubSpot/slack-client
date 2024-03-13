package com.hubspot.slack.client.models.events.links;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface LinkIF {
  String getDomain();

  String getUrl();
}

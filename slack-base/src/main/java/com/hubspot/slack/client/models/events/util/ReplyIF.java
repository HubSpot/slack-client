package com.hubspot.slack.client.models.events.util;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface ReplyIF {
  String getUser();
  String getTs();
}

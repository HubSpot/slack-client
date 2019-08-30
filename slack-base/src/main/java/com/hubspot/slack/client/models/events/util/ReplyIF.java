package com.hubspot.slack.client.models.events.util;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@Deprecated
public interface ReplyIF {
  String getUser();
  String getTs();
}

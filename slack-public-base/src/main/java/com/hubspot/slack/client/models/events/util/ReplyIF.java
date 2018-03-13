package com.hubspot.slack.client.models.events.util;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.styles.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface ReplyIF {
  String getUser();
  String getTs();
}

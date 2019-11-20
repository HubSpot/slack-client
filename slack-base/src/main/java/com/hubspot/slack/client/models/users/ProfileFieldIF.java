package com.hubspot.slack.client.models.users;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface ProfileFieldIF {

  String getValue();

  String getAlt();
}

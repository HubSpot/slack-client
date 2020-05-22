package com.hubspot.slack.client.models.users;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface ProfileFieldIF {

  String getValue();

  Optional<String> getAlt();
}

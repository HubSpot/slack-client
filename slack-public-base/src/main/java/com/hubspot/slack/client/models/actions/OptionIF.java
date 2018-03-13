package com.hubspot.slack.client.models.actions;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.styles.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface OptionIF {
  Optional<String> getText();
  String getValue();
}


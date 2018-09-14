package com.hubspot.slack.client.methods.params.auth;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface AuthRevokeParamsIF {
  default boolean isTest() {
    return false;
  }
}

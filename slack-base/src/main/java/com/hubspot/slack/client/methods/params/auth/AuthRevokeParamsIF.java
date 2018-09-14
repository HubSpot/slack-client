package com.hubspot.slack.client.methods.params.auth;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface AuthRevokeParamsIF {
  @Parameter
  default boolean isTest() {
    return false;
  }
}

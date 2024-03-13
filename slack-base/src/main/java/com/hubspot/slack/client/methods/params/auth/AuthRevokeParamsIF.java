package com.hubspot.slack.client.methods.params.auth;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface AuthRevokeParamsIF {
  default boolean isTest() {
    return false;
  }
}

package com.hubspot.slack.client.methods.params.users;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface UserEmailParamsIF {
  @Parameter String getEmail();
}

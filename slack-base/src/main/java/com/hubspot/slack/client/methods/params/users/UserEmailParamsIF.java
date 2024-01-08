package com.hubspot.slack.client.methods.params.users;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@HubSpotStyle
public interface UserEmailParamsIF {
  @Parameter
  String getEmail();
}

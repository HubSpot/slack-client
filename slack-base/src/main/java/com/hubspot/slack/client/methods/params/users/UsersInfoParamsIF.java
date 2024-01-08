package com.hubspot.slack.client.methods.params.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasUser;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@HubSpotStyle
public interface UsersInfoParamsIF extends HasUser {
  @Override
  @Parameter
  @JsonProperty("user")
  String getUserId();

  @Default
  @JsonProperty("include_local")
  default boolean getIncludeLocale() {
    return false;
  }
}

package com.hubspot.slack.client.methods.params.users;

import java.util.Optional;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasUser;

@Immutable
@HubSpotStyle
public interface UsersInfoParamsIF extends HasUser {
  @Override
  @Parameter
  @JsonProperty("user")
  String getUserId();

  @JsonProperty("include_local")
  default boolean getIncludeLocale() {
    return false;
  }
}

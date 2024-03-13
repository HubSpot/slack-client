package com.hubspot.slack.client.methods.params.im;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasUser;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ImOpenParamsIF extends HasUser {
  @Override
  @Parameter
  @JsonProperty("user")
  String getUserId();

  @Default
  default boolean getIncludeLocale() {
    return false;
  }

  @Default
  default boolean getReturnIm() {
    return true;
  }
}

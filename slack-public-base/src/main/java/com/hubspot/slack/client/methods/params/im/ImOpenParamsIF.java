package com.hubspot.slack.client.methods.params.im;

import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.styles.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasUser;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ImOpenParamsIF extends HasUser {
  @Override
  @Parameter
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

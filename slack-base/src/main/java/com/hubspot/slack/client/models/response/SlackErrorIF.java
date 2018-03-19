package com.hubspot.slack.client.models.response;

import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface SlackErrorIF {

  @Default
  default SlackErrorType getType() {
    return SlackErrorType.fromCode(getError());
  }

  @Parameter String getError();
}

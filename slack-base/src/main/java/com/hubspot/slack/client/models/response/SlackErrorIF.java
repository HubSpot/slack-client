package com.hubspot.slack.client.models.response;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@HubSpotStyle
public interface SlackErrorIF {
  @Default
  default SlackErrorType getType() {
    return SlackErrorType.get(getError());
  }

  @Parameter
  String getError();
}

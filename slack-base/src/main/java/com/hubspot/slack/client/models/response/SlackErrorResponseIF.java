package com.hubspot.slack.client.models.response;

import com.hubspot.immutables.style.HubSpotStyle;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface SlackErrorResponseIF extends SlackResponse {
  Optional<SlackError> getError();
  List<SlackError> getErrors();
}

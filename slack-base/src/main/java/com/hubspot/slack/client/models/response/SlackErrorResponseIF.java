package com.hubspot.slack.client.models.response;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface SlackErrorResponseIF extends SlackResponse {
  Optional<SlackError> getError();
  Optional<List<SlackError>> getErrors();
}

package com.hubspot.slack.client.methods.params.views;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.views.HomeTabViewPayload;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface PublishViewParamsIF {
  Optional<String> getUserId();
  HomeTabViewPayload getView();
  Optional<String> getHash();
}

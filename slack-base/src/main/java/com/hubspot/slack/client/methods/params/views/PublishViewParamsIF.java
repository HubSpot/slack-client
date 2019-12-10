package com.hubspot.slack.client.methods.params.views;

import java.util.Optional;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.views.HomeTabViewPayload;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface PublishViewParamsIF {
  @Value.Parameter
  String getUserId();
  @Value.Parameter
  HomeTabViewPayload getView();
  Optional<String> getHash();
}

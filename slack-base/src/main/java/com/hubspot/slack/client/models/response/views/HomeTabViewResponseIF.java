package com.hubspot.slack.client.models.response.views;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.views.HomeTabViewPayload;
import com.hubspot.slack.client.models.views.HomeTabViewPayloadBase;
import com.hubspot.slack.client.models.views.ViewPayloadBase;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface HomeTabViewResponseIF extends HomeTabViewPayloadBase, ViewResponseBase {
}

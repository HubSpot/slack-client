package com.hubspot.slack.client.models.interaction.views;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface ViewMultiExternalSelectIF extends ViewMultiSelect {
}

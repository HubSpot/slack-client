package com.hubspot.slack.client.models.interaction.views;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ViewPlainTextInputIF extends ViewInput {
  String getValue();
}

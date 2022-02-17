package com.hubspot.slack.client.models.interaction.views;

import java.time.LocalDate;
import java.util.Optional;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface ViewDatePickerIF extends ViewInput {
  Optional<LocalDate> getSelectedDate();
}

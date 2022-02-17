package com.hubspot.slack.client.models.interaction.views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.objects.Option;

import org.immutables.value.Value;

import java.time.LocalDate;
import java.util.Optional;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface ViewDatePickerIF extends ViewInput {
  Optional<LocalDate> getSelectedDate();

  @JsonIgnore
  default Optional<String> getStringValue() {
   return Optional.of(getSelectedDate().toString());
  };
}

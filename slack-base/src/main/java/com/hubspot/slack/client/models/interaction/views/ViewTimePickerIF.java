package com.hubspot.slack.client.models.interaction.views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface ViewTimePickerIF extends ViewInput {
  Optional<LocalTime> getSelectedTime();

  @JsonIgnore
  default Optional<String> getStringValue() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
    if (getSelectedTime().isPresent()) {
      return Optional.of(getSelectedTime().get().format(formatter));
    }
    return Optional.empty();
  }
}

package com.hubspot.slack.client.models.interaction.views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface ViewDatePickerIF extends ViewInput {
  Optional<LocalDate> getSelectedDate();

  @JsonIgnore
  default Optional<String> getStringValue() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
    if (getSelectedDate().isPresent()) {
      return Optional.of(getSelectedDate().get().format(formatter));
    }
    return Optional.empty();
  }
}

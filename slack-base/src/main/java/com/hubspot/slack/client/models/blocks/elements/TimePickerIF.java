package com.hubspot.slack.client.models.blocks.elements;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.objects.ConfirmationDialog;
import com.hubspot.slack.client.models.blocks.objects.Text;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

import java.time.LocalTime;
import java.util.Optional;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface TimePickerIF extends BlockElement, HasActionId {
  String TYPE = "timepicker";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter
  String getActionId();

  Optional<Text> getPlaceholder();

  Optional<String> getTimezone();

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  Optional<LocalTime> getInitialTime();

  @JsonProperty("confirm")
  Optional<ConfirmationDialog> getConfirmationDialog();
}

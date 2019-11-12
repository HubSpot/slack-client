package com.hubspot.slack.client.models.blocks.elements;

import java.time.LocalDate;
import java.util.Optional;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.objects.ConfirmationDialog;
import com.hubspot.slack.client.models.blocks.objects.Text;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface DatePickerIF extends BlockElement {
  String TYPE = "datepicker";

  @Override
  @Value.Default
  default String getType() {
    return TYPE;
  }

  String getActionId();

  Optional<Text> getPlaceholder();

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  Optional<LocalDate> getInitialDate();

  @JsonProperty("confirm")
  Optional<ConfirmationDialog> getConfirmationDialog();
}

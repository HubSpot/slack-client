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

import java.time.LocalDate;
import java.util.Optional;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface DateTimePickerIF extends BlockElement, HasActionId {
  String TYPE = "datetimepicker";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter
  String getActionId();

  Optional<Text> getPlaceholder();

  Optional<Integer> getInitialDateTime();

  @JsonProperty("confirm")
  Optional<ConfirmationDialog> getConfirmationDialog();

  Optional<Boolean> isFocusOnLoad();
}

package com.hubspot.slack.client.models.blocks.elements;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.objects.ConfirmationDialog;
import com.hubspot.slack.client.models.blocks.objects.Option;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface RadioButtonGroupIF extends BlockElement {
  String TYPE = "radio_buttons";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  String getActionId();

  List<Option> getOptions();

  Optional<Option> getInitialOption();

  @JsonProperty("confirm")
  Optional<ConfirmationDialog> getConfirmationDialog();
}

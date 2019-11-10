package com.hubspot.slack.client.models.blocks.elements;

import java.util.Optional;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.objects.ConfirmationDialog;
import com.hubspot.slack.client.models.blocks.objects.Option;
import com.hubspot.slack.client.models.blocks.objects.Text;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ExternalSelectMenuIF extends BlockElement {
  String TYPE = "external_select";

  @Override
  @Value.Default
  default String getType() {
    return TYPE;
  }

  Text getPlaceholder();

  String getActionId();

  Optional<Option> getInitialOption();

  Optional<Integer> getMinQueryLength();

  @JsonProperty("confirm")
  Optional<ConfirmationDialog> getConfirmationDialog();
}

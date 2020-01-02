package com.hubspot.slack.client.models.blocks.elements;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hubspot.slack.client.models.blocks.json.OptionalOptionOrOptionGroupDeserializer;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.objects.ConfirmationDialog;
import com.hubspot.slack.client.models.blocks.objects.Option;
import com.hubspot.slack.client.models.blocks.objects.OptionGroup;
import com.hubspot.slack.client.models.blocks.objects.OptionOrOptionGroup;
import com.hubspot.slack.client.models.blocks.objects.Text;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface StaticSelectMenuIF extends BlockElement, HasActionId {
  String TYPE = "static_select";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter
  Text getPlaceholder();

  @Value.Parameter
  String getActionId();

  @Value.Parameter
  List<Option> getOptions();

  List<OptionGroup> getOptionGroups();

  @JsonDeserialize(using = OptionalOptionOrOptionGroupDeserializer.class)
  Optional<OptionOrOptionGroup> getInitialOption();

  @JsonProperty("confirm")
  Optional<ConfirmationDialog> getConfirmationDialog();
}

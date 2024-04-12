package com.hubspot.slack.client.models.blocks.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.json.OptionOrOptionGroupDeserializer;
import com.hubspot.slack.client.models.blocks.objects.ConfirmationDialog;
import com.hubspot.slack.client.models.blocks.objects.OptionOrOptionGroup;
import com.hubspot.slack.client.models.blocks.objects.Text;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ExternalMultiSelectMenuIF extends BlockElement, HasActionId {
  String TYPE = "multi_external_select";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter
  Text getPlaceholder();

  @Value.Parameter
  String getActionId();

  Optional<Integer> getMinQueryLength();

  @JsonDeserialize(contentUsing = OptionOrOptionGroupDeserializer.class)
  List<OptionOrOptionGroup> getInitialOptions();

  @JsonProperty("confirm")
  Optional<ConfirmationDialog> getConfirmationDialog();
}

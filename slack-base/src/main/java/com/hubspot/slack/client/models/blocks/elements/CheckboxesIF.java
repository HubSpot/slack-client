package com.hubspot.slack.client.models.blocks.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.SlackBlockNormalizer;
import com.hubspot.slack.client.models.blocks.json.OptionOrOptionGroupDeserializer;
import com.hubspot.slack.client.models.blocks.objects.ConfirmationDialog;
import com.hubspot.slack.client.models.blocks.objects.Option;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface CheckboxesIF extends BlockElement, HasActionId {
  String TYPE = "checkboxes";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter
  String getActionId();

  @Value.Parameter
  List<Option> getOptions();

  @JsonDeserialize(contentUsing = OptionOrOptionGroupDeserializer.class)
  List<Option> getInitialOptions();

  @JsonProperty("confirm")
  Optional<ConfirmationDialog> getConfirmationDialog();

  @Check
  default CheckboxesIF validate() {
    return SlackBlockNormalizer.normalize(this);
  }
}

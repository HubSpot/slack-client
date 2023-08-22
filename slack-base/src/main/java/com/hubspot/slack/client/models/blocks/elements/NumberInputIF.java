package com.hubspot.slack.client.models.blocks.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.SlackBlockNormalizer;
import com.hubspot.slack.client.models.blocks.objects.Text;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

import java.util.Optional;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface NumberInputIF extends BlockElement, HasActionId {
  String TYPE = "number_input";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter(order = 0)
  String getActionId();

  @Value.Parameter(order = 1)
  @JsonProperty("is_decimal_allowed")
  boolean isDecimalAllowed();

  Optional<Text> getPlaceholder();

  Optional<String> getInitialValue();

  Optional<String> getMinValue();

  Optional<String> getMaxValue();

  Optional<Boolean> isFocusOnLoad();

  @Check
  default NumberInputIF validate() {
    return SlackBlockNormalizer.normalize(this);
  }
}

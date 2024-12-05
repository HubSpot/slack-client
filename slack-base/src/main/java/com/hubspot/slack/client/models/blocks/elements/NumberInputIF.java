package com.hubspot.slack.client.models.blocks.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.SlackBlockNormalizer;
import com.hubspot.slack.client.models.blocks.objects.Text;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

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

  @JsonSetter("focus_on_load")
  @JsonProperty("is_focus_on_load")
  Optional<Boolean> isFocusOnLoad();

  @Check
  default NumberInputIF validate() {
    return SlackBlockNormalizer.normalize(this);
  }
}

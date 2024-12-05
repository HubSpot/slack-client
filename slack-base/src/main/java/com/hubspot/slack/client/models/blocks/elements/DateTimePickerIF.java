package com.hubspot.slack.client.models.blocks.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.objects.ConfirmationDialog;
import com.hubspot.slack.client.models.blocks.objects.Text;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

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

  @JsonSetter("focus_on_load")
  @JsonProperty("is_focus_on_load")
  Optional<Boolean> isFocusOnLoad();
}

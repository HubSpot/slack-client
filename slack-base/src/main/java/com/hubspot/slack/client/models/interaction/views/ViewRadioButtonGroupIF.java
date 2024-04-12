package com.hubspot.slack.client.models.interaction.views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.json.OptionOrOptionGroupDeserializer;
import com.hubspot.slack.client.models.blocks.objects.Option;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface ViewRadioButtonGroupIF extends ViewInput {
  @JsonDeserialize(contentUsing = OptionOrOptionGroupDeserializer.class)
  Optional<Option> getSelectedOption();

  @JsonIgnore
  default Optional<String> getStringValue() {
    return getSelectedOption().map(Option::getValue);
  }
}

package com.hubspot.slack.client.models.interaction.views;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.json.OptionOrOptionGroupDeserializer;
import com.hubspot.slack.client.models.blocks.objects.Option;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface ViewCheckboxesIF extends ViewInput {
  @JsonDeserialize(contentUsing = OptionOrOptionGroupDeserializer.class)
  List<Option> getSelectedOptions();
}

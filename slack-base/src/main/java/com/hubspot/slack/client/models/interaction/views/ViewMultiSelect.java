package com.hubspot.slack.client.models.interaction.views;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hubspot.slack.client.models.blocks.json.OptionOrOptionGroupDeserializer;
import com.hubspot.slack.client.models.blocks.objects.Option;

public interface ViewMultiSelect extends ViewInput {
  @JsonDeserialize(contentUsing = OptionOrOptionGroupDeserializer.class)
  List<Option> getSelectedOptions();
}

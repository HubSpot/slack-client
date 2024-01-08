package com.hubspot.slack.client.models.interaction.views;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hubspot.slack.client.models.blocks.json.OptionOrOptionGroupDeserializer;
import com.hubspot.slack.client.models.blocks.objects.Option;
import java.util.List;

public interface ViewMultiSelect extends ViewInput {
  @JsonDeserialize(contentUsing = OptionOrOptionGroupDeserializer.class)
  List<Option> getSelectedOptions();
}

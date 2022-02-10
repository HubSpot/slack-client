package com.hubspot.slack.client.models.blocks.objects;

import java.util.List;

import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.SlackBlockNormalizer;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface OptionGroupIF extends OptionOrOptionGroup {
  @Value.Parameter
  Text getLabel();

  @Value.Parameter
  List<Option> getOptions();

  @Check
  default OptionGroupIF validate() {
    return SlackBlockNormalizer.normalize(this);
  }
}

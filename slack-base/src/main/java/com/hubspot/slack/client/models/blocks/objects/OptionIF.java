package com.hubspot.slack.client.models.blocks.objects;

import java.util.Optional;

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
public interface OptionIF extends OptionOrOptionGroup{
  @Value.Parameter
  Text getText();

  @Value.Parameter
  String getValue();

  Optional<Text> getDescription();

  Optional<String> getUrl();

  @Check
  default OptionIF validate() {
    return SlackBlockNormalizer.normalize(this);
  }
}

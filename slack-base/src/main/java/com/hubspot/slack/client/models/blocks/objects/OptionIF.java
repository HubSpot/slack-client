package com.hubspot.slack.client.models.blocks.objects;

import java.util.Optional;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface OptionIF extends OptionOrOptionGroup{
  @Value.Parameter
  Text getText();

  @Value.Parameter
  String getValue();

  Optional<String> getUrl();
}

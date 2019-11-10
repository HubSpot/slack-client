package com.hubspot.slack.client.models.blocks.objects;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ConfirmationDialogIF extends OptionOrOptionGroup {
  Text getTitle();

  Text getText();

  @JsonProperty("confirm")
  Text getConfirmButtonText();

  @JsonProperty("deny")
  Text getDenyButtonText();
}

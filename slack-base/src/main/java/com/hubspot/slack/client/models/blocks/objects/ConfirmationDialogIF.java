package com.hubspot.slack.client.models.blocks.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.Style;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ConfirmationDialogIF extends CompositionObject {
  @Value.Parameter
  Text getTitle();

  @Value.Parameter
  Text getText();

  @JsonProperty("confirm")
  @Value.Parameter
  Text getConfirmButtonText();

  @JsonProperty("deny")
  @Value.Parameter
  Text getDenyButtonText();

  Optional<Style> getStyle();
}

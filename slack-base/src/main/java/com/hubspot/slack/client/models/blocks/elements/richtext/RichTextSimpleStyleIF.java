package com.hubspot.slack.client.models.blocks.elements.richtext;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextSimpleStyleIF {
  Optional<Boolean> getBold();

  Optional<Boolean> getItalic();

  Optional<Boolean> getStrike();

  Optional<Boolean> getCode();
}

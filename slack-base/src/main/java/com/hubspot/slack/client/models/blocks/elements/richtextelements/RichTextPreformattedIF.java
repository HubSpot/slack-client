package com.hubspot.slack.client.models.blocks.elements.richtextelements;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextPreformattedIF extends RichTextObject {
  String TYPE = "rich_text_preformatted";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @JsonProperty("elements")
  ImmutableList<RichTextElement> getElements();

  Optional<Integer> getBorder();

  Optional<String> getLanguage();
}

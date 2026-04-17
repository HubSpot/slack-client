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
public interface RichTextListIF extends RichTextObject {
  String TYPE = "rich_text_list";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @JsonProperty("elements")
  ImmutableList<RichTextSection> getElements();

  @Value.Parameter
  RichTextListStyle getStyle();

  Optional<Integer> getIndent();

  Optional<Integer> getBorder();

  Optional<Integer> getOffset();
}

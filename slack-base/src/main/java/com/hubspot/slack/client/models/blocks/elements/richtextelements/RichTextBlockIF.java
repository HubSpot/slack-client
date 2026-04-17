package com.hubspot.slack.client.models.blocks.elements.richtextelements;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.Block;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * Slack's Rich text block displays formatted, structured representation of text.
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/rich-text-block/">Rich text block Docs</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextBlockIF extends Block {
  String TYPE = "rich_text";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @JsonProperty("elements")
  ImmutableList<RichTextObject> getElements();
}

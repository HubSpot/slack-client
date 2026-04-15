package com.hubspot.slack.client.models.blocks.elements;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * Slack's Rich text block displays formatted, structured representation of text..
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/rich-text-block/">Rich text block Docs</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface UrlSourceIF extends BlockElement {
  String TYPE = "url";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter(order = 1)
  String getUrl();

  @Value.Parameter(order = 2)
  String getText();
}

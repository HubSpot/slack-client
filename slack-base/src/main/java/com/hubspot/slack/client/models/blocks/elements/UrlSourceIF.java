package com.hubspot.slack.client.models.blocks.elements;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * Slack's URL source element displays a URL source for referencing within a task card block.
 * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/url-source-element">URL source element</a>
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

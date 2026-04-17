package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * Slack's markdown block type renders standard Markdown natively (e.g. **bold**, ## headings).
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/markdown-block">Slack Markdown Block Docs</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface MarkdownBlockIF extends Block {
  String TYPE = "markdown";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  /**
   * The standard markdown-formatted text. The cumulative limit for all markdown blocks in a single payload is 12,000 characters.
   */
  @Value.Parameter
  @JsonProperty("text")
  String getText();
}

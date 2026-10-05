package com.hubspot.slack.client.models.blocks.elements.richtextelements;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * Slack's citation element renders as an AI citation (file, external, web, message, or memory).
 * This is a rich text element, compatible only with the {@code rich_text} block. It must be
 * used within the {@code rich_text_list}, {@code rich_text_quote}, or {@code rich_text_section}
 * block element within the {@code rich_text} block's {@code elements} array.
 * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/citation-element">Citation element</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextCitationElementIF extends RichTextElement {
  String TYPE = "citation";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  String getUrl();

  String getText();

  int getIndex();

  CitationDetails getDetails();

  Optional<Boolean> getFromLlm();

  @JsonProperty("is_slack_url")
  Optional<Boolean> getIsSlackUrl();
}

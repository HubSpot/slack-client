package com.hubspot.slack.client.models.blocks.elements.richtextelements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * Slack's list record element renders as a link to a Slack list record.
 * This is a rich text element, compatible only with the {@code rich_text} block. It must be
 * used within the {@code rich_text_list}, {@code rich_text_quote}, or {@code rich_text_section}
 * block element within the {@code rich_text} block's {@code elements} array.
 * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/list-record-element">List record element</a>
 */
@Immutable
@HubSpotStyle
@Value.Enclosing
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextListRecordElementIF extends RichTextElement {
  String TYPE = "list_record";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter
  String getFileId();

  Optional<String> getRecordId();

  Optional<String> getViewId();

  Optional<String> getText();

  Optional<String> getUrl();

  Optional<StyleIF> getStyle();

  @Immutable
  @HubSpotStyle
  @JsonNaming(SnakeCaseStrategy.class)
  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  @JsonDeserialize(as = RichTextListRecordElement.Style.class)
  interface StyleIF {
    Optional<Boolean> getBold();

    Optional<Boolean> getItalic();

    Optional<Boolean> getStrike();

    Optional<Boolean> getHighlight();

    Optional<Boolean> getClientHighlight();

    Optional<Boolean> getUnderline();

    Optional<Boolean> getUnlink();
  }
}

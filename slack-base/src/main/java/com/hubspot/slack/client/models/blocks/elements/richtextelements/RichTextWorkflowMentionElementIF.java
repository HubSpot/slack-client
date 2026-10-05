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
 * Slack's workflow mention element renders as a link to a workflow.
 * This is a rich text element, compatible only with the {@code rich_text} block. It must be
 * used within the {@code rich_text_list}, {@code rich_text_quote}, or {@code rich_text_section}
 * block element within the {@code rich_text} block's {@code elements} array.
 * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/workflow-mention-element">Workflow mention element</a>
 */
@Immutable
@HubSpotStyle
@Value.Enclosing
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextWorkflowMentionElementIF extends RichTextElement {
  String TYPE = "workflow_mention";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  String getWorkflowId();

  String getFunctionTriggerId();

  String getText();

  Optional<String> getUrl();

  Optional<String> getChannelId();

  Optional<String> getTs();

  Optional<StyleIF> getStyle();

  @Immutable
  @HubSpotStyle
  @JsonNaming(SnakeCaseStrategy.class)
  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  @JsonDeserialize(as = RichTextWorkflowMentionElement.Style.class)
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

package com.hubspot.slack.client.models.blocks.elements.richtextelements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * Slack's file element renders as a link to a Slack file.
 * This is a rich text element, compatible only with the {@code rich_text} block. It must be
 * used within the {@code rich_text_list}, {@code rich_text_quote}, or {@code rich_text_section}
 * block element within the {@code rich_text} block's {@code elements} array.
 * <p>Note: this is distinct from the {@code file} <em>block</em> (the {@code File} block class); both
 * use {@code type: "file"} but the block references a remote file via {@code external_id}/{@code source},
 * whereas this element links a Slack file via {@code file_id} inside rich text.
 * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/file-element">File element</a>
 */
@Immutable
@HubSpotStyle
@Value.Enclosing
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextFileElementIF extends RichTextElement {
  String TYPE = "file";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter
  String getFileId();

  Optional<String> getText();

  Optional<String> getUrl();

  @JsonProperty("is_skill_invocation")
  Optional<Boolean> getIsSkillInvocation();

  Optional<StyleIF> getStyle();

  @Immutable
  @HubSpotStyle
  @JsonNaming(SnakeCaseStrategy.class)
  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  @JsonDeserialize(as = RichTextFileElement.Style.class)
  interface StyleIF {
    Optional<Boolean> getBold();

    Optional<Boolean> getItalic();

    Optional<Boolean> getStrike();

    Optional<Boolean> getHighlight();

    Optional<Boolean> getClientHighlight();

    Optional<Boolean> getUnderline();
  }
}

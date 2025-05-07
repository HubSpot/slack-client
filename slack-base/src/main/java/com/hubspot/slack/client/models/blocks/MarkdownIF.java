package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface MarkdownIF extends Block {
  String TYPE = "markdown";

  /**
   * The type of block. For a markdown block, type is always markdown.
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: true
   */
  @Override
  @Derived
  default String getType() {
    return TYPE;
  }

  /**
   * The standard markdown-formatted text. Limit 12,000 characters max.
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: true
   */
  @Parameter
  String getText();

  @Check
  default void check() {
    Preconditions.checkState(
      getText().length() < BlockElementLengthLimits.MAX_MARKDOWN_LENGTH.getLimit(),
      "Text length must be less than " +
      BlockElementLengthLimits.MAX_MARKDOWN_LENGTH.getLimit() +
      " characters"
    );
  }
}

package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.elements.Image;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextBlock;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

/**
 * Slack's layout block — renders its child blocks in a grid of up to 5 rows, each with up to 3
 * items, with optional per-item width and alignment and optional grid lines.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/layout-block">Layout block</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public interface LayoutBlockIF extends Block {
  String TYPE = "layout";

  /**
   * Block types Slack allows inside a layout block's {@code fallback}. Narrower than the set of
   * types allowed as row-item children.
   */
  ImmutableSet<String> ALLOWED_FALLBACK_TYPES = ImmutableSet.of(
    Context.TYPE,
    Divider.TYPE,
    Header.TYPE,
    Image.TYPE,
    RichTextBlock.TYPE,
    Section.TYPE,
    Table.TYPE,
    VideoBlock.TYPE
  );

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  String getLabel();

  @JsonProperty("is_label_visible")
  Optional<Boolean> isLabelVisible();

  Optional<LayoutGridLines> getGridLines();

  ImmutableList<LayoutRow> getRows();

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  ImmutableList<Block> getFallback();

  @Check
  default void check() {
    Preconditions.checkState(
      !Strings.nullToEmpty(getLabel()).trim().isEmpty(),
      "label is required"
    );
    Preconditions.checkState(
      getLabel().length() <= BlockElementLengthLimits.MAX_LAYOUT_LABEL_LENGTH.getLimit(),
      "label cannot exceed %s characters",
      BlockElementLengthLimits.MAX_LAYOUT_LABEL_LENGTH.getLimit()
    );
    Preconditions.checkState(
      !getRows().isEmpty() &&
      getRows().size() <= BlockElementLengthLimits.MAX_LAYOUT_ROWS.getLimit(),
      "a layout block must contain between 1 and %s rows",
      BlockElementLengthLimits.MAX_LAYOUT_ROWS.getLimit()
    );
    getFallback()
      .forEach(block ->
        Preconditions.checkState(
          ALLOWED_FALLBACK_TYPES.contains(block.getType()),
          "fallback does not support block type '%s'; allowed types: %s",
          block.getType(),
          ALLOWED_FALLBACK_TYPES
        )
      );
  }
}

package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

/**
 * A single row within a {@link LayoutBlockIF}: 1-3 items rendered left to right.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/layout-block">Layout block</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface LayoutRowIF {
  ImmutableList<LayoutItem> getItems();

  @Check
  default void check() {
    Preconditions.checkState(
      !getItems().isEmpty() &&
      getItems().size() <= BlockElementLengthLimits.MAX_LAYOUT_ROW_ITEMS.getLimit(),
      "a layout row must contain between 1 and %s items",
      BlockElementLengthLimits.MAX_LAYOUT_ROW_ITEMS.getLimit()
    );
  }
}

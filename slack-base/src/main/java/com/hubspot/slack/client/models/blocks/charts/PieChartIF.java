package com.hubspot.slack.client.models.blocks.charts;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

/**
 * A {@code pie} chart for the data visualization block.
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-visualization-block/#pie">Data visualization block — pie</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface PieChartIF extends Chart {
  String TYPE = "pie";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  ImmutableList<ChartSegment> getSegments();

  @Check
  default void check() {
    Preconditions.checkState(
      !getSegments().isEmpty() && getSegments().size() <= 12,
      "pie chart must contain between 1 and 12 segments"
    );
  }
}

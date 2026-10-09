package com.hubspot.slack.client.models.blocks.charts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

/**
 * A single data point in a chart data series (used by bar/area/line charts).
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-visualization-block/#data-point">Data visualization block — data point</a>
 */
@Immutable
@HubSpotStyle
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(SnakeCaseStrategy.class)
public interface ChartDataPointIF {
  String getLabel();

  double getValue();

  @Check
  default void check() {
    Preconditions.checkState(
      getLabel().length() <= 20,
      "chart data point label cannot exceed 20 characters"
    );
  }
}

package com.hubspot.slack.client.models.blocks.charts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

/**
 * A named series of data points in a bar/area/line chart.
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-visualization-block/#data-series">Data visualization block — data series</a>
 */
@Immutable
@HubSpotStyle
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(SnakeCaseStrategy.class)
public interface ChartDataSeriesIF {
  String getName();

  ImmutableList<ChartDataPoint> getData();

  @Check
  default void check() {
    Preconditions.checkState(
      getName().length() <= 20,
      "chart data series name cannot exceed 20 characters"
    );
    Preconditions.checkState(
      !getData().isEmpty() && getData().size() <= 20,
      "chart data series must contain between 1 and 20 data points"
    );
  }
}

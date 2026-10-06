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
 * A {@code bar} chart for the data visualization block.
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-visualization-block/#bar">Data visualization block — bar</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface BarChartIF extends Chart {
  String TYPE = "bar";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  ImmutableList<ChartDataSeries> getSeries();

  ChartAxisConfig getAxisConfig();

  @Check
  default void check() {
    Charts.checkSeries(getSeries());
  }
}

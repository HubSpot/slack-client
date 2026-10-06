package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.charts.Chart;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

/**
 * Slack's data visualization block renders a pie, bar, area, or line chart.
 * Limit of 2 data visualization blocks per message.
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-visualization-block">Data visualization block</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface DataVisualizationBlockIF extends Block {
  String TYPE = "data_visualization";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  String getTitle();

  Chart getChart();

  @Check
  default void check() {
    Preconditions.checkState(
      getTitle().length() <= 50,
      "data_visualization title cannot exceed 50 characters"
    );
  }
}

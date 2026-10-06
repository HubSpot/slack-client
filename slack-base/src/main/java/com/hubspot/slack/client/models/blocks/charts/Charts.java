package com.hubspot.slack.client.models.blocks.charts;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/** Shared validation for the bar/area/line chart variants. */
final class Charts {

  private Charts() {}

  static void checkSeries(ImmutableList<ChartDataSeries> series) {
    Preconditions.checkState(
      !series.isEmpty() && series.size() <= 12,
      "chart must contain between 1 and 12 data series"
    );
    long distinctNames = series.stream().map(ChartDataSeries::getName).distinct().count();
    Preconditions.checkState(
      distinctNames == series.size(),
      "chart data series names must be unique"
    );
  }
}

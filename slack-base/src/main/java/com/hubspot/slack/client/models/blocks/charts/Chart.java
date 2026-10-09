package com.hubspot.slack.client.models.blocks.charts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The polymorphic {@code chart} payload of a data visualization block. The concrete type is
 * discriminated by {@code type}: {@code pie}, {@code bar}, {@code area}, or {@code line}.
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-visualization-block">Data visualization block</a>
 */
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  property = "type",
  defaultImpl = UnknownChart.class
)
@JsonSubTypes(
  {
    @JsonSubTypes.Type(value = PieChart.class, name = PieChart.TYPE),
    @JsonSubTypes.Type(value = BarChart.class, name = BarChart.TYPE),
    @JsonSubTypes.Type(value = AreaChart.class, name = AreaChart.TYPE),
    @JsonSubTypes.Type(value = LineChart.class, name = LineChart.TYPE),
  }
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface Chart {
  String getType();
}

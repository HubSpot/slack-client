package com.hubspot.slack.client.models.blocks.charts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

/**
 * A single segment of a pie chart (used by the data visualization block).
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-visualization-block/#segment">Data visualization block — segment</a>
 */
@Immutable
@HubSpotStyle
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(SnakeCaseStrategy.class)
public interface ChartSegmentIF {
  String getLabel();

  double getValue();

  @Check
  default void check() {
    Preconditions.checkState(
      getLabel().length() <= 20,
      "pie chart segment label cannot exceed 20 characters"
    );
    Preconditions.checkState(getValue() > 0, "pie chart segment value must be > 0");
  }
}

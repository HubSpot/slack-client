package com.hubspot.slack.client.models.blocks.charts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

/**
 * Axis configuration for a bar/area/line chart.
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-visualization-block/#axis-config">Data visualization block — axis config</a>
 */
@Immutable
@HubSpotStyle
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(SnakeCaseStrategy.class)
public interface ChartAxisConfigIF {
  ImmutableList<String> getCategories();

  @JsonProperty("x_label")
  Optional<String> getXLabel();

  @JsonProperty("y_label")
  Optional<String> getYLabel();

  @Check
  default void check() {
    Preconditions.checkState(
      !getCategories().isEmpty(),
      "chart axis_config categories must not be empty"
    );
    getCategories()
      .forEach(category ->
        Preconditions.checkState(
          category.length() <= 20,
          "chart axis_config category label cannot exceed 20 characters"
        )
      );
    getXLabel()
      .ifPresent(label ->
        Preconditions.checkState(
          label.length() <= 50,
          "chart axis_config x_label cannot exceed 50 characters"
        )
      );
    getYLabel()
      .ifPresent(label ->
        Preconditions.checkState(
          label.length() <= 50,
          "chart axis_config y_label cannot exceed 50 characters"
        )
      );
  }
}

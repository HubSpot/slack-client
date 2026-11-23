package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

/**
 * A single item within a {@link LayoutBlockIF} row: one child block plus optional sizing/alignment.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/layout-block">Layout block</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public interface LayoutItemIF {
  Block getBlock();

  Optional<LayoutItemWidth> getWidth();

  Optional<LayoutHorizontalAlignment> getHorizontalAlignment();
}

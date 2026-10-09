package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

/**
 * Slack's carousel block — a horizontally-scrolling container for related cards.
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/carousel-block">Carousel block</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface CarouselIF extends Block {
  String TYPE = "carousel";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  ImmutableList<Card> getElements();

  @Check
  default void check() {
    Preconditions.checkState(
      !getElements().isEmpty() && getElements().size() <= 10,
      "carousel must contain between 1 and 10 cards"
    );
  }
}

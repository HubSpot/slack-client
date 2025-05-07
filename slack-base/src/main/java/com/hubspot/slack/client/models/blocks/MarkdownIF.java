package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.hubspot.immutables.style.HubSpotStyle;
import javax.annotation.Nullable;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface MarkdownIF extends Block {
  String TYPE = "markdown";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Default
  @Nullable
  @Value.Parameter
  default String getText() {
    return null;
  }

  @Value.Check
  default void check() {
    Preconditions.checkState(
      getText() != null && getText().length() <= 12000,
      "The text length of a markdown block cannot exceed 12,000 characters"
    );
  }
}

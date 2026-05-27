package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.elements.contextactionselements.ContextActionsElement;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface ContextActionsBlockIF extends Block {
  String TYPE = "context_actions";
  int MAX_ELEMENTS = 5;
  int MAX_BLOCK_ID_LENGTH = 255;

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter
  ImmutableList<ContextActionsElement> getElements();

  @Check
  default void check() {
    Preconditions.checkState(
      !getElements().isEmpty(),
      "A context_actions block must contain at least one element"
    );
    Preconditions.checkState(
      getElements().size() <= MAX_ELEMENTS,
      "A context_actions block cannot contain more than %s elements",
      MAX_ELEMENTS
    );
    getBlockId()
      .ifPresent(blockId ->
        Preconditions.checkState(
          blockId.length() <= MAX_BLOCK_ID_LENGTH,
          "block_id cannot exceed %s characters",
          MAX_BLOCK_ID_LENGTH
        )
      );
  }
}

package com.hubspot.slack.client.models.blocks.elements.contextactionselements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface FeedbackButtonsElementIF extends ContextActionsElement {
  String TYPE = "feedback_buttons";
  int MAX_ACTION_ID_LENGTH = 255;

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter(order = 0)
  FeedbackButton getPositiveButton();

  @Value.Parameter(order = 1)
  FeedbackButton getNegativeButton();

  Optional<String> getActionId();

  @Check
  default void check() {
    getActionId()
      .ifPresent(actionId ->
        Preconditions.checkState(
          actionId.length() <= MAX_ACTION_ID_LENGTH,
          "action_id cannot exceed %s characters",
          MAX_ACTION_ID_LENGTH
        )
      );
  }
}

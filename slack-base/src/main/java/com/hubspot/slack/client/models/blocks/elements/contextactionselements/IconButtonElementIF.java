package com.hubspot.slack.client.models.blocks.elements.contextactionselements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.objects.ConfirmationDialog;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface IconButtonElementIF extends ContextActionsElement {
  String TYPE = "icon_button";
  int MAX_ACTION_ID_LENGTH = 255;
  int MAX_VALUE_LENGTH = 2000;
  int MAX_ACCESSIBILITY_LABEL_LENGTH = 75;

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter(order = 0)
  String getIcon();

  @Value.Parameter(order = 1)
  Text getText();

  Optional<String> getActionId();

  Optional<String> getValue();

  Optional<ConfirmationDialog> getConfirm();

  Optional<String> getAccessibilityLabel();

  ImmutableList<String> getVisibleToUserIds();

  @Check
  default void check() {
    Preconditions.checkState(
      getText().getType() == TextType.PLAIN_TEXT,
      "Icon button text must be of type plain_text"
    );
    getActionId()
      .ifPresent(actionId ->
        Preconditions.checkState(
          actionId.length() <= MAX_ACTION_ID_LENGTH,
          "action_id cannot exceed %s characters",
          MAX_ACTION_ID_LENGTH
        )
      );
    getValue()
      .ifPresent(value ->
        Preconditions.checkState(
          value.length() <= MAX_VALUE_LENGTH,
          "value cannot exceed %s characters",
          MAX_VALUE_LENGTH
        )
      );
    getAccessibilityLabel()
      .ifPresent(label ->
        Preconditions.checkState(
          label.length() <= MAX_ACCESSIBILITY_LABEL_LENGTH,
          "accessibility_label cannot exceed %s characters",
          MAX_ACCESSIBILITY_LABEL_LENGTH
        )
      );
  }
}

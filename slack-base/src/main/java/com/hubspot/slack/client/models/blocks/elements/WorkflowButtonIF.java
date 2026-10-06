package com.hubspot.slack.client.models.blocks.elements;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;
import com.hubspot.slack.client.models.blocks.objects.WorkflowObject;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

/**
 * Slack's workflow button runs a link trigger with customizable inputs when clicked.
 * Works with the {@code section} and {@code actions} blocks; available on the Messages surface.
 * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/workflow-button-element">Workflow button element</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface WorkflowButtonIF extends BlockElement, HasActionId {
  String TYPE = "workflow_button";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter(order = 1)
  Text getText();

  @Value.Parameter(order = 2)
  WorkflowObject getWorkflow();

  @Value.Parameter(order = 3)
  String getActionId();

  Optional<String> getStyle();

  Optional<String> getAccessibilityLabel();

  @Check
  default void check() {
    Preconditions.checkState(
      getText().getType() == TextType.PLAIN_TEXT,
      "workflow_button text must be a plain_text text object"
    );
    Preconditions.checkState(
      getText().getText().length() <= 75,
      "workflow_button text cannot exceed 75 characters"
    );
    Preconditions.checkState(
      getActionId().length() <= 255,
      "workflow_button action_id cannot exceed 255 characters"
    );
    getAccessibilityLabel()
      .ifPresent(label ->
        Preconditions.checkState(
          label.length() <= 75,
          "workflow_button accessibility_label cannot exceed 75 characters"
        )
      );
  }
}

package com.hubspot.slack.client.models.blocks.objects;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * A workflow object describing the workflow that runs when a workflow button is clicked.
 * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/workflow-button-element">Workflow button element</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface WorkflowIF {
  @Value.Parameter
  WorkflowTrigger getTrigger();
}

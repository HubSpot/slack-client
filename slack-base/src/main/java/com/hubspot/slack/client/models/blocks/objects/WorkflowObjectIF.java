package com.hubspot.slack.client.models.blocks.objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * Slack's workflow composition object — describes the workflow that runs when a workflow button is
 * clicked. Contains a single required {@link TriggerObject}.
 * @see <a href="https://docs.slack.dev/reference/block-kit/composition-objects/workflow-object">Workflow object</a>
 */
@Immutable
@HubSpotStyle
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(SnakeCaseStrategy.class)
public interface WorkflowObjectIF {
  @Value.Parameter
  TriggerObject getTrigger();
}

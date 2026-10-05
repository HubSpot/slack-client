package com.hubspot.slack.client.models.blocks.objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.List;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * A link trigger for a workflow, referenced by the {@link Workflow} object inside a workflow button.
 * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/workflow-button-element">Workflow button element</a>
 */
@Immutable
@HubSpotStyle
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(SnakeCaseStrategy.class)
public interface WorkflowTriggerIF {
  @Value.Parameter
  String getUrl();

  List<WorkflowInputParameter> getCustomizableInputParameters();
}

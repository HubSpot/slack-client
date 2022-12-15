package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.SlackChannel;
import com.hubspot.slack.client.models.workflows.WorkflowStep;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface WorkflowStepEditIF extends SlackInteractiveCallback {
  String getTriggerId();

  WorkflowStep getWorkflowStep();

  @Override
  default SlackChannel getChannel() {
    return null;
  }
}

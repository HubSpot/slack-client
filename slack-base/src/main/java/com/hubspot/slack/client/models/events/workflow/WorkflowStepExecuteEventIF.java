package com.hubspot.slack.client.models.events.workflow;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.events.SlackEvent;
import com.hubspot.slack.client.models.workflows.WorkflowStep;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = WorkflowStepExecuteEvent.class)
public interface WorkflowStepExecuteEventIF extends SlackEvent {

  WorkflowStep getWorkflowStep();

  String getCallbackId();

  @Override
  default String getTs() {
    return null;
  }


}

package com.hubspot.slack.client.models.events.workflow;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.workflows.WorkflowConfiguration;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = WorkflowPublishedEvent.class)
public interface WorkflowPublishedEventIF extends WorkflowEventBase {

  WorkflowConfiguration getWorkflowPublishedConfiguration();

}

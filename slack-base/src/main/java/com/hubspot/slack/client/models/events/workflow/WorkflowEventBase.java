package com.hubspot.slack.client.models.events.workflow;

import com.hubspot.slack.client.models.events.SlackEvent;

public interface WorkflowEventBase extends SlackEvent {

  String getWorkflowId();

  String getEventTs();

  @Override
  default String getTs() {
    return null;
  }
}

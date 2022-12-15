package com.hubspot.slack.client.models.workflows;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface WorkflowStepIF  {

  Optional<String> getWorkflowId();
  Optional<String> getStepId();

  Optional<String> getWorkflowInstanceId();

  Optional<String> getWorkflowStepExecuteId();

  Optional<String> getStepName();
  Optional<String> getStepImageUrl();

  Map<String, Input> getInputs();

  List<Output> getOutputs();

}

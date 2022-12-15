package com.hubspot.slack.client.models.workflows;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Map;
import org.immutables.value.Value;
import org.immutables.value.Value.Default;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface InputIF {

// according to the docs, this can be any valid JSON value. https://api.slack.com/reference/workflows/workflow_step
  JsonNode getValue();

  Map<String, Object> getVariables();
  @Default
  default boolean getSkipVariableReplacement() {
    return true;
  }

}

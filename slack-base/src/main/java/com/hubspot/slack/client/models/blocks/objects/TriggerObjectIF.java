package com.hubspot.slack.client.models.blocks.objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.List;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * Slack's trigger composition object — a link trigger referenced by a {@link WorkflowObject}.
 * @see <a href="https://docs.slack.dev/reference/block-kit/composition-objects/trigger-object">Trigger object</a>
 */
@Immutable
@HubSpotStyle
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(SnakeCaseStrategy.class)
public interface TriggerObjectIF {
  @Value.Parameter
  String getUrl();

  List<WorkflowInputParameter> getCustomizableInputParameters();
}

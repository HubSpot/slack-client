package com.hubspot.slack.client.models.blocks.objects;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * A single customizable input parameter ({@code name}/{@code value}) in a {@link TriggerObject}'s
 * {@code customizable_input_parameters} array.
 * @see <a href="https://docs.slack.dev/reference/block-kit/composition-objects/trigger-object#fields">Trigger object — fields</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface WorkflowInputParameterIF {
  @Value.Parameter(order = 1)
  String getName();

  @Value.Parameter(order = 2)
  String getValue();
}

package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.elements.UrlSource;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextBlock;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * Slack's task_card block displays a single task, representing a single action.
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/task-card-block">Task card block Docs</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface TaskCardBlockIF extends Block {
  String TYPE = "task_card";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter(order = 1)
  @JsonProperty("task_id")
  String getTaskId();

  @Value.Parameter(order = 2)
  String getTitle();

  @JsonProperty("details")
  Optional<RichTextBlock> getDetails();

  @JsonProperty("output")
  Optional<RichTextBlock> getOutput();

  @JsonProperty("sources")
  ImmutableList<UrlSource> getSources();

  @JsonProperty("status")
  Optional<TaskCardBlockStatus> getStatus();
}

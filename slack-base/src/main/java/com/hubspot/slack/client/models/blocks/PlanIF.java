package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

/**
 * Slack's plan block — a collection of related task cards.
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/plan-block">Plan block</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface PlanIF extends Block {
  String TYPE = "plan";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  String getTitle();

  ImmutableList<TaskCardBlock> getTasks();

  @Check
  default void check() {
    Preconditions.checkState(
      getTasks().size() <= 50,
      "plan cannot contain more than 50 tasks"
    );
    long distinctTaskIds = getTasks()
      .stream()
      .map(TaskCardBlock::getTaskId)
      .distinct()
      .count();
    Preconditions.checkState(
      distinctTaskIds == getTasks().size(),
      "each task_id in a plan must be unique"
    );
  }
}

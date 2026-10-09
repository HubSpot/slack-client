package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.annotation.JsonInclude;
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
 * A task within a {@link Plan}. Per Slack's plan block docs, a plan's task objects carry no
 * {@code type} field, so this is modeled as a plain object rather than reusing the polymorphic
 * {@code task_card} block ({@code TaskCardBlock}). That lets it both serialize without a {@code type}
 * and deserialize inbound plan tasks that omit one (an incoming {@code type} is ignored). Fields
 * mirror the task card.
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/plan-block">Plan block</a>
 */
@Immutable
@HubSpotStyle
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(SnakeCaseStrategy.class)
public interface PlanTaskIF {
  @Value.Parameter(order = 1)
  String getTaskId();

  @Value.Parameter(order = 2)
  String getTitle();

  Optional<RichTextBlock> getDetails();

  Optional<RichTextBlock> getOutput();

  ImmutableList<UrlSource> getSources();

  Optional<TaskCardBlockStatus> getStatus();
}

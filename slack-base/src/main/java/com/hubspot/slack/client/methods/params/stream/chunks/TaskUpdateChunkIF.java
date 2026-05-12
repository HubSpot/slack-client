package com.hubspot.slack.client.methods.params.stream.chunks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.TaskCardBlockStatus;
import com.hubspot.slack.client.models.blocks.elements.UrlSource;
import java.util.Optional;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface TaskUpdateChunkIF extends StreamChunk {
  String TYPE = "task_update";

  @Override
  @Derived
  default String getType() {
    return TYPE;
  }

  String getId();

  String getTitle();

  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  Optional<TaskCardBlockStatus> getStatus();

  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  Optional<String> getDetails();

  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  Optional<String> getOutput();

  ImmutableList<UrlSource> getSources();
}

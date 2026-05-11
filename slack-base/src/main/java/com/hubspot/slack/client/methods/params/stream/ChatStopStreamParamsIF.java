package com.hubspot.slack.client.methods.params.stream;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.Block;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ChatStopStreamParamsIF extends StreamParams {
  String getTs();

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  ImmutableList<Block> getBlocks();

  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  Optional<StreamMetadata> getMetadata();
}

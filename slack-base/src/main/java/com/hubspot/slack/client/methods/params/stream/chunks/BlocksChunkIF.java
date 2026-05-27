package com.hubspot.slack.client.methods.params.stream.chunks;

import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.Block;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface BlocksChunkIF extends StreamChunk {
  String TYPE = "blocks";

  @Override
  @Derived
  default String getType() {
    return TYPE;
  }

  ImmutableList<Block> getBlocks();
}

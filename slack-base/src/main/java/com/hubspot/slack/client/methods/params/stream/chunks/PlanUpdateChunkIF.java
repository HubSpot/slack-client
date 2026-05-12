package com.hubspot.slack.client.methods.params.stream.chunks;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface PlanUpdateChunkIF extends StreamChunk {
  String TYPE = "plan_update";

  @Override
  @Derived
  default String getType() {
    return TYPE;
  }

  String getTitle();
}

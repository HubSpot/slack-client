package com.hubspot.slack.client.models.views;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.Block;
import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface HomeTabViewPayloadIF
  extends HomeTabViewPayloadBase, ViewPayloadJsonBase {
  @Override
  @Value.Parameter
  List<Block> getBlocks();
}

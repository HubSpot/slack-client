package com.hubspot.slack.client.models.views;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.Block;
import com.hubspot.slack.client.models.blocks.objects.Text;
import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface ModalViewPayloadIF extends ModalViewPayloadBase, ViewPayloadJsonBase {
  @Override
  @Value.Parameter
  Text getTitle();

  @Override
  @Value.Parameter
  List<Block> getBlocks();
}

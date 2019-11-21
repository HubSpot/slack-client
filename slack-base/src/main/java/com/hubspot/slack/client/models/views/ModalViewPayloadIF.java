package com.hubspot.slack.client.models.views;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.Block;
import com.hubspot.slack.client.models.blocks.objects.Text;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface ModalViewPayloadIF extends ViewPayloadBase {
  String TYPE = "modal";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Override
  @Value.Parameter
  List<Block> getBlocks();

  @Value.Parameter
  Text getTitle();

  @JsonProperty("close")
  Optional<Text> getCloseButtonText();

  @JsonProperty("submit")
  Optional<Text> getSubmitButtonText();

  Optional<Boolean> getClearOnClose();

  Optional<Boolean> getNotifyOnClose();
}

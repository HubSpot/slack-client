package com.hubspot.slack.client.models.views;

import com.hubspot.slack.client.models.blocks.Block;
import java.util.List;
import java.util.Optional;

public interface ViewPayloadBase {
  String getType();
  List<Block> getBlocks();
  Optional<String> getPrivateMetadata();
  Optional<String> getCallbackId();
  Optional<String> getExternalId();
}

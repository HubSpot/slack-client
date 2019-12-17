package com.hubspot.slack.client.models.blocks;

import java.util.Optional;

public class UnknownBlock implements Block {
  public static final String TYPE = "unknown";

  protected UnknownBlock() {
  }

  @Override
  public String getType() {
    return TYPE;
  }

  @Override
  public Optional<String> getBlockId() {
    return Optional.empty();
  }
}

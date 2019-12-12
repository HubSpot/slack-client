package com.hubspot.slack.client.models.blocks;

import java.util.Optional;

public class UnknownBlock implements Block {
  protected UnknownBlock() {
  }

  @Override
  public String getType() {
    return "";
  }

  @Override
  public Optional<String> getBlockId() {
    return Optional.empty();
  }
}

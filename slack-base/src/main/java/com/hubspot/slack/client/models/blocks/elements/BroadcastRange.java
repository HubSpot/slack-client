package com.hubspot.slack.client.models.blocks.elements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hubspot.slack.client.enums.EnumIndex;
import com.hubspot.slack.client.enums.UnmappedKeyException;
import java.util.Optional;

public enum BroadcastRange {
  HERE,
  CHANNEL,
  EVERYONE;

  private static final EnumIndex<String, BroadcastRange> INDEX = new EnumIndex<>(
    BroadcastRange.class,
    BroadcastRange::key
  );

  @JsonCreator
  public static BroadcastRange get(String key) throws UnmappedKeyException {
    return INDEX.get(key.toLowerCase());
  }

  public static Optional<BroadcastRange> find(String key) {
    return INDEX.find(key.toLowerCase());
  }

  @JsonValue
  public String key() {
    return name().toLowerCase();
  }
}

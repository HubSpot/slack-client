package com.hubspot.slack.client.models.conversations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hubspot.slack.client.enums.EnumIndex;
import com.hubspot.slack.client.enums.UnmappedKeyException;

public enum ConversationType {
  PUBLIC_CHANNEL,
  PRIVATE_CHANNEL,
  MPIM,
  IM;

  private static final EnumIndex<String, ConversationType> INDEX = new EnumIndex<>(
    ConversationType.class,
    ConversationType::toString
  );

  @JsonCreator
  public static ConversationType get(String key) throws UnmappedKeyException {
    return INDEX.get(key.toLowerCase());
  }

  @Override
  @JsonValue
  public String toString() {
    return name().toLowerCase();
  }
}

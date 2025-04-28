package com.hubspot.slack.client.models.blocks.elements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hubspot.slack.client.enums.EnumIndex;
import com.hubspot.slack.client.enums.UnmappedKeyException;
import java.util.Optional;

public enum ListStyle {
  BULLET,
  ORDERED;

  private static final EnumIndex<String, ListStyle> INDEX = new EnumIndex<>(
    ListStyle.class,
    ListStyle::key
  );

  @JsonCreator
  public static ListStyle get(String key) throws UnmappedKeyException {
    return INDEX.get(key.toLowerCase());
  }

  public static Optional<ListStyle> find(String key) {
    return INDEX.find(key.toLowerCase());
  }

  @JsonValue
  public String key() {
    return name().toLowerCase();
  }
}

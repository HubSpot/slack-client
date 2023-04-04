package com.hubspot.slack.client.models.blocks;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hubspot.slack.client.enums.EnumIndex;
import com.hubspot.slack.client.enums.UnmappedKeyException;

public enum Style {
  DEFAULT,
  PRIMARY,
  DANGER;

  private static final EnumIndex<String, Style> INDEX = new EnumIndex<>(Style.class, Style::key);

  @JsonCreator
  public static Style get(String key) throws UnmappedKeyException {
    return INDEX.get(key.toLowerCase());
  }

  public static Optional<Style> find(String key) {
    return INDEX.find(key.toLowerCase());
  }

  @JsonValue
  public String key() {
    return name().toLowerCase();
  }
}

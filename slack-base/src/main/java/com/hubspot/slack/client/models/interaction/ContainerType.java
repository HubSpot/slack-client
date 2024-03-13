package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hubspot.slack.client.enums.EnumIndex;

public enum ContainerType {
  VIEW,
  MESSAGE,
  UNKNOWN;

  private static final EnumIndex<String, ContainerType> INDEX = new EnumIndex<>(
    ContainerType.class,
    ContainerType::toString
  );

  @JsonCreator
  public static ContainerType get(String key) {
    return INDEX.find(key).orElse(ContainerType.UNKNOWN);
  }

  @Override
  @JsonValue
  public String toString() {
    return name().toLowerCase();
  }
}

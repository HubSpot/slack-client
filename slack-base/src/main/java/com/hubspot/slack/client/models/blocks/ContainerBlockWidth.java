package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ContainerBlockWidth {
  NARROW("narrow"),
  STANDARD("standard"),
  WIDE("wide"),
  FULL("full");

  private final String value;

  ContainerBlockWidth(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}

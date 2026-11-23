package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LayoutGridLines {
  NONE("none"),
  ROWS("rows"),
  OUTER("outer");

  private final String value;

  LayoutGridLines(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}

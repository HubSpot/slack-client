package com.hubspot.slack.client.models.blocks.table;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TableColumnAlign {
  LEFT("left"),
  CENTER("center"),
  RIGHT("right");

  private final String value;

  TableColumnAlign(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}

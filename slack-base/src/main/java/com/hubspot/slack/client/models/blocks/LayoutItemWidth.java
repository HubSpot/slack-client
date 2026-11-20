package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LayoutItemWidth {
  NARROW("narrow"),
  STANDARD("standard"),
  WIDE("wide");

  private final String value;

  LayoutItemWidth(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}

package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LayoutHorizontalAlignment {
  START("start"),
  CENTER("center"),
  END("end");

  private final String value;

  LayoutHorizontalAlignment(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}

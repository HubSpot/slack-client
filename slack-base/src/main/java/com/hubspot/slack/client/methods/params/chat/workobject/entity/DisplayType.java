package com.hubspot.slack.client.methods.params.chat.workobject.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DisplayType {
  DOCUMENT("Document"),
  FILE("File"),
  TASK("Task");

  private final String value;

  DisplayType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}

package com.hubspot.slack.client.methods.params.chat.workobject;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EntityType {
  FILE("slack#/entities/file"),
  TASK("slack#/entities/task"),
  INCIDENT("slack#/entities/incident"),
  CONTENT_ITEM("slack#/entities/content_item"),
  ITEM("slack#/entities/item");

  private final String value;

  EntityType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}

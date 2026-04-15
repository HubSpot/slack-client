package com.hubspot.slack.client.models.blocks.elements.richtextelements;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RichTextListStyle {
  BULLET("bullet"),
  ORDERED("ordered");

  private final String value;

  RichTextListStyle(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}

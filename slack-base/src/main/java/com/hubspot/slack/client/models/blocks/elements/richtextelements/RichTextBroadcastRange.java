package com.hubspot.slack.client.models.blocks.elements.richtextelements;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RichTextBroadcastRange {
  HERE("here"),
  CHANNEL("channel"),
  EVERYONE("everyone");

  private final String value;

  RichTextBroadcastRange(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}

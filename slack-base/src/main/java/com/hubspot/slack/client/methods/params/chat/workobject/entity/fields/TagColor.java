package com.hubspot.slack.client.methods.params.chat.workobject.entity.fields;

public enum TagColor {
  RED("red"),
  YELLOW("yellow"),
  GREEN("green"),
  GRAY("gray"),
  BLUE("blue");

  private final String value;

  TagColor(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}

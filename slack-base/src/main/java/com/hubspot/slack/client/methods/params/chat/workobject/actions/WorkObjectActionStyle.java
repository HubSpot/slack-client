package com.hubspot.slack.client.methods.params.chat.workobject.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum WorkObjectActionStyle {
  PRIMARY,
  DANGER;

  @JsonCreator
  public static WorkObjectActionStyle parse(String result) {
    return WorkObjectActionStyle.valueOf(result.toUpperCase());
  }

  @JsonValue
  public String value() {
    return name().toLowerCase();
  }
}

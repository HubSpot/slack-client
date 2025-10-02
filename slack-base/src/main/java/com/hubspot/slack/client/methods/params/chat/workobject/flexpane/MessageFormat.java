package com.hubspot.slack.client.methods.params.chat.workobject.flexpane;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageFormat {
  STRING,
  MARKDOWN;

  @JsonCreator
  public static MessageFormat parse(String result) {
    return MessageFormat.valueOf(result.toUpperCase());
  }

  @JsonValue
  public String value() {
    return name().toLowerCase();
  }
}

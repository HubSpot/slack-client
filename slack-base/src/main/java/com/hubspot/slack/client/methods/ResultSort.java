package com.hubspot.slack.client.methods;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ResultSort {
  TIMESTAMP,
  SCORE;

  @JsonCreator
  public static ResultSort parse(String result) {
    return ResultSort.valueOf(result);
  }

  @JsonValue
  public String value() {
    return name().toLowerCase();
  }
}

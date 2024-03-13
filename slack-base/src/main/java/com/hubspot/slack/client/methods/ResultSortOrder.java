package com.hubspot.slack.client.methods;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ResultSortOrder {
  ASC,
  DESC;

  @JsonCreator
  public static ResultSortOrder parse(String result) {
    return ResultSortOrder.valueOf(result);
  }

  @JsonValue
  public String value() {
    return name().toLowerCase();
  }
}

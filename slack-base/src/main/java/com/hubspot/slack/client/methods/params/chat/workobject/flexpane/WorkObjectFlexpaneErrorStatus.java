package com.hubspot.slack.client.methods.params.chat.workobject.flexpane;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum WorkObjectFlexpaneErrorStatus {
  RESTRICTED,
  INTERNAL_ERROR,
  NOT_FOUND,
  CUSTOM,
  CUSTOM_PARTIAL_VIEW,
  TIMEOUT,
  EDIT_ERROR;

  @JsonCreator
  public static WorkObjectFlexpaneErrorStatus parse(String result) {
    return WorkObjectFlexpaneErrorStatus.valueOf(result.toUpperCase());
  }

  @JsonValue
  public String value() {
    return name().toLowerCase();
  }
}

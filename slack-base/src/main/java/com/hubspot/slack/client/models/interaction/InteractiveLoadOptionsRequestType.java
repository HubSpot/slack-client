package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hubspot.slack.client.enums.EnumIndex;

public enum InteractiveLoadOptionsRequestType {
  BLOCK_SUGGESTION,
  DIALOG_SUGGESTION,
  INTERACTIVE_MESSAGE,
  UNKNOWN
  ;

  private static final EnumIndex<String, InteractiveLoadOptionsRequestType> INDEX = new EnumIndex<>(InteractiveLoadOptionsRequestType.class, InteractiveLoadOptionsRequestType::toString);

  @JsonCreator
  public static InteractiveLoadOptionsRequestType get(String key) {
    return INDEX.find(key).orElse(InteractiveLoadOptionsRequestType.UNKNOWN);
  }

  @Override
  @JsonValue
  public String toString() {
    return name().toLowerCase();
  }
}

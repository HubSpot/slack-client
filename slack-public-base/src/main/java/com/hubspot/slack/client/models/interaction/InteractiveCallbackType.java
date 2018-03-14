package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hubspot.slack.client.enums.EnumIndex;

public enum InteractiveCallbackType {
  INTERACTIVE_MESSAGE,
  DIALOG_SUBMISSION,
  UNKNOWN
  ;

  private static final EnumIndex<String, InteractiveCallbackType> INDEX = new EnumIndex<>(InteractiveCallbackType.class, InteractiveCallbackType::toString);

  @JsonCreator
  public static InteractiveCallbackType get(String key) {
    return INDEX.find(key).orElse(InteractiveCallbackType.UNKNOWN);
  }

  @Override
  @JsonValue
  public String toString() {
    return name().toLowerCase();
  }
}

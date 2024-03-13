package com.hubspot.slack.client.models.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hubspot.slack.client.enums.EnumIndex;
import com.hubspot.slack.client.enums.UnmappedKeyException;

public enum ActionType {
  /**
   * Show a button
   */
  BUTTON,

  /**
   * Show a dropdown/selector
   */
  SELECT;

  private static final EnumIndex<String, ActionType> INDEX = new EnumIndex<>(
    ActionType.class,
    ActionType::toString
  );

  @JsonCreator
  public static ActionType get(String key) throws UnmappedKeyException {
    return INDEX.get(key);
  }

  @Override
  @JsonValue
  public String toString() {
    return name().toLowerCase();
  }
}

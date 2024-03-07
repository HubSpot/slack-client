package com.hubspot.slack.client.models.interaction.views;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hubspot.slack.client.enums.EnumIndex;

public enum ViewInputType {
  PLAIN_TEXT_INPUT,
  EMAIL_TEXT_INPUT,
  URL_TEXT_INPUT,
  NUMBER_INPUT,
  DATEPICKER,
  TIMEPICKER,
  DATETIMEPICKER,
  RADIO_BUTTONS,
  USERS_SELECT,
  EXTERNAL_SELECT,
  CHECKBOXES,
  CONVERSATIONS_SELECT,
  STATIC_SELECT,
  MULTI_STATIC_SELECT,
  MULTI_EXTERNAL_SELECT,
  UNKNOWN;

  private static final EnumIndex<String, ViewInputType> INDEX = new EnumIndex<>(
    ViewInputType.class,
    ViewInputType::toString
  );

  @JsonCreator
  public static ViewInputType get(String key) {
    return INDEX.find(key).orElse(ViewInputType.UNKNOWN);
  }

  @Override
  @JsonValue
  public String toString() {
    return name().toLowerCase();
  }
}

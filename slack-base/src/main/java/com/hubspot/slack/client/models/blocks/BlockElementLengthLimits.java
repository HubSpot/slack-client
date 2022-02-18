package com.hubspot.slack.client.models.blocks;

public enum BlockElementLengthLimits {
  MAX_INPUT_LABEL_LENGTH(2000),
  MAX_HINT_LENGTH(2000),
  MAX_OPTION_GROUPS_NUMBER(100),
  MAX_OPTIONS_NUMBER(100),
  MAX_PLAIN_TEXT_PLACEHOLDER_LENGTH(150),
  MAX_OPTION_TEXT_LENGTH(75),
  MAX_OPTION_GROUP_LABEL_LENGTH(75),
  MAX_OPTION_VALUE_LENGTH(75),
  MAX_CHECKBOXES_NUMBER(10),
  MAX_RADIO_BUTTONS_NUMBER(10);

  private final int limit;

  BlockElementLengthLimits(int limit) {
    this.limit = limit;
  }

  public int getLimit() {
    return limit;
  }
}

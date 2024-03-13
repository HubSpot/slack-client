package com.hubspot.slack.client.models.dialog.form.elements;

public enum SlackDialogFormElementLengthLimits {
  MAX_LABEL_LENGTH(24),
  MAX_NAME_LENGTH(300),
  MAX_PLACEHOLDER_LENGTH(150),
  MAX_HINT_LENGTH(150),
  MAX_TEXT_ELEMENT_VALUE_LENGTH(150),
  MAX_TEXT_AREA_ELEMENT_VALUE_LENGTH(3000),
  MAX_OPTION_LABEL_LENGTH(75),
  MAX_OPTION_VALUE_LENGTH(75),
  MAX_OPTIONS_NUMBER(100),
  MAX_OPTION_GROUPS_NUMBER(100);

  private final int limit;

  SlackDialogFormElementLengthLimits(int limit) {
    this.limit = limit;
  }

  public int getLimit() {
    return limit;
  }
}

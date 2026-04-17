package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskCardBlockStatus {
  PENDING("pending"),
  IN_PROGRESS("in_progress"),
  COMPLETE("complete"),
  ERROR("error");

  private final String value;

  TaskCardBlockStatus(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}

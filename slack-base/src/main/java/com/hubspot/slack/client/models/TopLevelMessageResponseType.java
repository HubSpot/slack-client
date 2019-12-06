package com.hubspot.slack.client.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TopLevelMessageResponseType {
  EPHEMERAL,
  IN_CHANNEL,
  ;

  @Override
  @JsonValue
  public String toString() {
    return name().toLowerCase();
  }
}
package com.hubspot.slack.client.models.workflows;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum OutputType {
  CHANNEL,
  TEXT,
  UNKNOWN,
  USER;

  @JsonCreator
  public static OutputType fromSlackName(String slackName) {
    return Arrays.stream(OutputType.values())
        .filter(enumVal -> enumVal.toString().equalsIgnoreCase(slackName))
        .findFirst()
        .orElse(UNKNOWN);
  }

  @JsonValue
  public String toSlackName() {
    return this.toString().toLowerCase();
  }

  }

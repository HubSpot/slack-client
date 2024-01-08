package com.hubspot.slack.client.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum ChannelType {
  GROUP("G"),
  CHANNEL("C");

  private final String slackName;

  ChannelType(String slackName) {
    this.slackName = slackName;
  }

  @JsonCreator
  public static ChannelType fromSlackName(String slackName) {
    return Arrays
      .stream(ChannelType.values())
      .filter(enumVal -> enumVal.slackName.toLowerCase().equals(slackName.toLowerCase()))
      .findFirst()
      .orElse(CHANNEL);
  }

  @JsonValue
  public String toSlackName() {
    return slackName;
  }
}

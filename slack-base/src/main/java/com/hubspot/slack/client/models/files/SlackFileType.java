package com.hubspot.slack.client.models.files;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SlackFileType {
  TEXT("text"),
  GIF("gif"),
  CSV("csv"),
  JPG("jpg"),
  PNG("png"),
  UNKNOWN("unknown"),
  ;

  final String type;

  SlackFileType(String type) {
    this.type = type;
  }

  @JsonValue
  public String getType() {
    return type;
  }

  @JsonCreator
  public static SlackFileType parse(String field) {
    return Stream.of(values())
        .filter(val -> val.getType().equalsIgnoreCase(field))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(field + " doesn't match any known slack file type"));
  }
}

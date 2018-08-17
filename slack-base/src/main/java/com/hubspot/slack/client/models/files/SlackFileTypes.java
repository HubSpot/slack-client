package com.hubspot.slack.client.models.files;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SlackFileTypes {
  TEXT("text"),
  GIF("gif"),
  ;

  final String type;

  SlackFileTypes(String type) {
    this.type = type;
  }

  @JsonValue
  public String getType() {
    return type;
  }

  @JsonCreator
  public static SlackFileTypes parse(String field) {
    return Stream.of(values())
        .filter(val -> val.getType().equalsIgnoreCase(field))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(field + " doesn't match any known slack file type"));
  }
}

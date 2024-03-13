package com.hubspot.slack.client.models.bookmarks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BookmarkType {
  LINK;

  @Override
  @JsonValue
  public String toString() {
    return name().toLowerCase();
  }

  @JsonCreator
  public static BookmarkType parse(String value) {
    return BookmarkType.valueOf(value.toUpperCase());
  }
}

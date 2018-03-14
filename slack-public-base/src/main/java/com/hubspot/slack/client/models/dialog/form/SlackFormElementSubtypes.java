package com.hubspot.slack.client.models.dialog.form;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hubspot.slack.client.enums.EnumIndex;
import com.hubspot.slack.client.enums.UnmappedKeyException;

public enum SlackFormElementSubtypes {
  EMAIL,
  NUMBER,
  TEL,
  URL,
  ;


  private static final EnumIndex<String, SlackFormElementSubtypes> INDEX = new EnumIndex<>(SlackFormElementSubtypes.class, SlackFormElementSubtypes::key);

  @JsonCreator
  public static SlackFormElementSubtypes get(String key) throws UnmappedKeyException {
    return INDEX.get(key.toLowerCase());
  }

  public static Optional<SlackFormElementSubtypes> find(String key) {
    return INDEX.find(key.toLowerCase());
  }

  @JsonValue
  public String key() {
    return name().toLowerCase();
  }
}

package com.hubspot.slack.client.models.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hubspot.slack.client.enums.EnumIndex;
import com.hubspot.slack.client.enums.UnmappedKeyException;
import java.util.Optional;

public enum ButtonStyle {
  /**
   * Buttons will look simple.
   */
  DEFAULT,

  /**
   * Use this sparingly, when the button represents a key action to accomplish.
   * You should probably only ever have one primary button within a set.
   */
  PRIMARY,

  /**
   * Use this when the consequence of the button click will result in the destruction of something, like a piece of data stored on your servers.
   * Use even more sparingly than primary.
   */
  DANGER;

  private static final EnumIndex<String, ButtonStyle> INDEX = new EnumIndex<>(
    ButtonStyle.class,
    ButtonStyle::toString
  );

  @JsonCreator
  public static ButtonStyle get(String key) throws UnmappedKeyException {
    return INDEX.get(key);
  }

  public static Optional<ButtonStyle> find(String key) {
    return INDEX.find(key);
  }

  @Override
  @JsonValue
  public String toString() {
    return name().toLowerCase();
  }
}

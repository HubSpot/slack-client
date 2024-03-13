package com.hubspot.slack.client.models.dialog.form;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hubspot.slack.client.enums.EnumIndex;
import com.hubspot.slack.client.enums.UnmappedKeyException;
import java.util.Optional;

/**
 * Descriptions from https://api.slack.com/dialogs
 */
public enum SlackFormElementTypes {
  /**
   * Text inputs work well with concise free-form answers and inputs with unestablished bounds, such as names, email addresses, or ticket titles if your form is used for something like a bug tracker.
   */
  TEXT,

  /**
   * Text Areas are best when the expected answer is long — over 150 characters or so —. It is best for open-ended and qualitative questions.
   */
  TEXTAREA,

  /**
   * Select menus are for multiple choice questions, and great for close-ended quantitative questions, such as office locations, priority level, meal preference, etc.
   */
  SELECT;

  private static final EnumIndex<String, SlackFormElementTypes> INDEX = new EnumIndex<>(
    SlackFormElementTypes.class,
    SlackFormElementTypes::key
  );

  @JsonCreator
  public static SlackFormElementTypes get(String key) throws UnmappedKeyException {
    return INDEX.get(key.toLowerCase());
  }

  public static Optional<SlackFormElementTypes> find(String key) {
    return INDEX.find(key.toLowerCase());
  }

  @JsonValue
  public String key() {
    return name().toLowerCase();
  }
}

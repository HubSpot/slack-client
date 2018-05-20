package com.hubspot.slack.client.models.dialog.form.elements;

import java.util.Optional;

import org.immutables.value.Value.Default;

import com.hubspot.slack.client.models.dialog.form.SlackFormElementSubtypes;

public abstract class AbstractSlackDialogFormTextElement extends SlackDialogFormElement {
  public abstract Optional<SlackFormElementSubtypes> getSubtype();
  public abstract Optional<String> getHint();

  @Default
  public int getMinLength() {
    return 0;
  }

  @Default
  public int getMaxLength() {
    return 150;
  }

  public abstract Optional<String> getValue();

  protected void validateBaseTextElementProps() {
    super.validateBaseElementProperties();
    if (getMinLength() < 0) {
      throw new IllegalStateException("Min length cannot be negative, got " + getMinLength());
    }

    if (getMaxLength() < 0) {
      throw new IllegalStateException("Max length cannot be negative, got " + getMaxLength());
    }

    if (getMinLength() > getMaxLength()) {
      throw new IllegalStateException("Min length must be <= max length, got " + getMinLength() + ", " + getMaxLength());
    }

    if (getHint().isPresent() && getHint().get().length() > 150) {
      throw new IllegalStateException("Hint cannot exceed 150 chars, got '" + getHint().get() + "'");
    }
  }
}

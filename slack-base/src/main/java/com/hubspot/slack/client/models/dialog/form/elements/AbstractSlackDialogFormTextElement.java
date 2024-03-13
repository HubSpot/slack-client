package com.hubspot.slack.client.models.dialog.form.elements;

import com.hubspot.slack.client.models.dialog.form.SlackFormElementSubtypes;
import java.util.Optional;
import org.immutables.value.Value.Default;

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

  protected void validateBaseTextElementProps(
    AbstractSlackDialogFormTextElement normalized
  ) {
    super.validateBaseElementProperties(normalized);
    int normalizedMinLength = normalized.getMinLength();
    if (normalizedMinLength < 0) {
      throw new IllegalStateException(
        "Min length cannot be negative, got " + normalizedMinLength
      );
    }

    int normalizedMaxLength = normalized.getMaxLength();
    if (normalizedMaxLength < 0) {
      throw new IllegalStateException(
        "Max length cannot be negative, got " + normalizedMaxLength
      );
    }

    if (normalizedMinLength > normalizedMaxLength) {
      throw new IllegalStateException(
        "Min length must be <= max length, got " +
        normalizedMinLength +
        ", " +
        normalizedMaxLength
      );
    }
  }
}

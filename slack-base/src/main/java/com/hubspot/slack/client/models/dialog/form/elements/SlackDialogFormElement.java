package com.hubspot.slack.client.models.dialog.form.elements;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.slack.client.models.dialog.form.SlackFormElementTypes;

public abstract class SlackDialogFormElement implements HasLabel {
  public abstract SlackFormElementTypes getType();

  public abstract String getName();

  public abstract Optional<String> getPlaceholder();

  @JsonProperty("optional")
  public abstract Optional<Boolean> isOptional();

  protected void validateBaseElementProperties(SlackDialogFormElement normalized) {
    String normalizedLabel = normalized.getLabel();
    int maxLabelLength = SlackDialogFormElementLengthLimits.MAX_LABEL_LENGTH.getLimit();
    if (normalizedLabel.length() > maxLabelLength) {
      String errorMessage = String.format("Label cannot exceed %s chars, got %s", maxLabelLength, normalizedLabel);
      throw new IllegalStateException(errorMessage);
    }

    String normalizedName = normalized.getName();
    int maxNameLength = SlackDialogFormElementLengthLimits.MAX_NAME_LENGTH.getLimit();
    if (normalizedName.length() > maxNameLength) {
      String errorMessage = String.format("Name cannot exceed %s chars, got %s", maxNameLength, normalizedName);
      throw new IllegalStateException(errorMessage);
    }

    Optional<String> normalizedPlaceholder = normalized.getPlaceholder();
    int maxPlaceholderLength = SlackDialogFormElementLengthLimits.MAX_PLACEHOLDER_LENGTH.getLimit();
    if (normalizedPlaceholder.isPresent() && normalizedPlaceholder.get().length() > maxPlaceholderLength) {
      String errorMessage = String.format(
          "Placeholder cannot exceed %s chars, got %s",
          maxPlaceholderLength,
          normalizedPlaceholder.get()
      );
      throw new IllegalStateException(errorMessage);
    }
  }
}

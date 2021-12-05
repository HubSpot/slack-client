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
    if (normalizedLabel.length() > SlackDialogFormElementLengthLimits.MAX_LABEL_LENGTH.getLimit()) {
      throw new IllegalStateException("Label cannot exceed 24 chars, got " + normalizedLabel);
    }

    String normalizedName = normalized.getName();
    if (normalizedName.length() > SlackDialogFormElementLengthLimits.MAX_NAME_LENGTH.getLimit()) {
      throw new IllegalStateException("Name cannot exceed 300 chars, got " + normalizedName);
    }

    Optional<String> normalizedPlaceholder = normalized.getPlaceholder();
    if (normalizedPlaceholder.isPresent() && normalizedPlaceholder.get().length() > SlackDialogFormElementLengthLimits.MAX_PLACEHOLDER_LENGTH
        .getLimit()) {
      throw new IllegalStateException("Placeholder length cannot exceed 150 chars, got " + normalizedPlaceholder.get());
    }
  }
}

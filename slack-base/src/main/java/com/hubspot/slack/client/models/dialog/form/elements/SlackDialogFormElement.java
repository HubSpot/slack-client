package com.hubspot.slack.client.models.dialog.form.elements;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.slack.client.models.dialog.form.SlackFormElementTypes;

public abstract class SlackDialogFormElement {
  public abstract SlackFormElementTypes getType();
  public abstract String getName();
  public abstract String getLabel();
  public abstract Optional<String> getPlaceholder();

  @JsonProperty("optional")
  public abstract Optional<Boolean> isOptional();

  protected void validateBaseElementProperties() {
    if (getLabel().length() > 24) {
      throw new IllegalStateException("Label cannot exceed 24 chars, got " + getLabel());
    }

    if (getName().length() > 300) {
      throw new IllegalStateException("Name cannot exceed 300 chars, got " + getName());
    }

    if (getPlaceholder().isPresent() && getPlaceholder().get().length() > 150) {
      throw new IllegalStateException("Placeholder length cannot exceed 150 chars, got " + getPlaceholder().get());
    }
  }
}

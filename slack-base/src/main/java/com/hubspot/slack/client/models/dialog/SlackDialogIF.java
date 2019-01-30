package com.hubspot.slack.client.models.dialog;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Strings;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.dialog.form.elements.SlackDialogFormElement;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(Include.NON_EMPTY)
public interface SlackDialogIF {
  String getTitle();
  String getCallbackId();
  List<SlackDialogFormElement> getElements();
  Optional<String> getState();
  Optional<String> getSubmitLabel();
  Optional<Boolean> getNotifyOnCancel();

  @Check
  default void validate() {
    if (Strings.isNullOrEmpty(getTitle())) {
      throw new IllegalStateException("Title must be present");
    }

    if (getTitle().length() > 24) {
      throw new IllegalStateException("Title cannot exceed 24 chars, got " + getTitle());
    }

    if (Strings.isNullOrEmpty(getCallbackId())) {
      throw new IllegalStateException("Callback id must be present");
    }

    if (getCallbackId().length() > 255) {
      throw new IllegalStateException("Callback id cannot exceed 255 chars, got " + getCallbackId());
    }

    if (getElements().isEmpty()) {
      throw new IllegalStateException("At least one form element required");
    }

    if (getElements().size() > 5) {
      throw new IllegalStateException("At most 5 form elements allowed, got " + getElements().size());
    }

    getState()
        .ifPresent(state -> {
          if (state.length() > 3000) {
            throw new IllegalStateException("State cannot exist 3000 chars, got " + state.length());
          }
        });

    getSubmitLabel()
        .ifPresent(submitLabel -> {
          if (submitLabel.length() > 24) {
            throw new IllegalStateException("Submit label cannot exceed 24 chars, got " + submitLabel.length());
          }
        });
  }
}

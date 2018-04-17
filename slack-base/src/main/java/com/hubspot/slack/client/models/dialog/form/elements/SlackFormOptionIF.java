package com.hubspot.slack.client.models.dialog.form.elements;

import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Strings;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface SlackFormOptionIF {
  String getLabel();
  String getValue();

  @Check
  default void validate() {
    if (Strings.isNullOrEmpty(getLabel())) {
      throw new IllegalStateException("Must provide a label");
    }

    String label = getLabel();
    if (label.length() > 24) {
      throw new IllegalStateException("Label cannot exceed 24 chars - '" + label + "'");
    }

    if (Strings.isNullOrEmpty(getValue())) {
      throw new IllegalStateException("Must provide a value");
    }

    String value = getValue();
    if (value.length() > 75) {
      throw new IllegalStateException("Value cannot exceed 75 chars - '" + value + "'");
    }
  }
}

package com.hubspot.slack.client.models.dialog.form.elements;

import java.util.List;

import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Strings;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface SlackFormOptionGroupIF {
  String getLabel();
  List<SlackFormOption> getOptions();

  @Check
  default void validate() {
    String label = getLabel();
    int numOptionGroups = getOptions().size();

    if (Strings.isNullOrEmpty(label)) {
      throw new IllegalStateException("Must provide a label");
    }

    if (label.length() > 75) {
      throw new IllegalStateException("Label cannot exceed 75 chars - '" + label + "'");
    }

    if (numOptionGroups > 100) {
      throw new IllegalStateException("Cannot have more than 100 option groups. Has " + numOptionGroups);
    }
  }
}

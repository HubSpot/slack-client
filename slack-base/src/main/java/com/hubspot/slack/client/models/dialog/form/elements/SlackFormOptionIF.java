package com.hubspot.slack.client.models.dialog.form.elements;

import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Strings;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.dialog.form.elements.helper.SlackDialogElementNormalizer;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface SlackFormOptionIF extends HasLabel{
  String getValue();

  @Check
  default SlackFormOptionIF validate() {
    SlackFormOptionIF normalized = SlackDialogElementNormalizer.normalize(this);
    if (Strings.isNullOrEmpty(getLabel())) {
      throw new IllegalStateException("Must provide a label");
    }

    String label = normalized.getLabel();
    if (label.length() > SlackDialogFormElementLengthLimits.MAX_OPTION_LABEL_LENGTH.getLimit()) {
      throw new IllegalStateException("Label cannot exceed 75 chars - '" + label + "'");
    }

    if (Strings.isNullOrEmpty(getValue())) {
      throw new IllegalStateException("Must provide a value");
    }

    String value = normalized.getValue();
    if (value.length() > SlackDialogFormElementLengthLimits.MAX_OPTION_VALUE_LENGTH.getLimit()) {
      throw new IllegalStateException("Value cannot exceed 75 chars - '" + value + "'");
    }
    return normalized;
  }
}

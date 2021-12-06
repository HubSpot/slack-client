package com.hubspot.slack.client.models.dialog.form.elements;

import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Strings;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.dialog.form.elements.helpers.SlackDialogElementNormalizer;

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
    int maxOptionLabelLength = SlackDialogFormElementLengthLimits.MAX_OPTION_LABEL_LENGTH.getLimit();
    if (label.length() > maxOptionLabelLength) {
      String errorMessage = String.format("Label cannot exceed %s chars - '%s'", maxOptionLabelLength, label);
      throw new IllegalStateException(errorMessage);
    }

    if (Strings.isNullOrEmpty(getValue())) {
      throw new IllegalStateException("Must provide a value");
    }

    String value = normalized.getValue();
    int maxOptionValueLength = SlackDialogFormElementLengthLimits.MAX_OPTION_VALUE_LENGTH.getLimit();
    if (value.length() > maxOptionValueLength) {
      String errorMessage = String.format("Value cannot exceed %s chars - '%s'", maxOptionValueLength, value);
      throw new IllegalStateException(errorMessage);
    }
    return normalized;
  }
}

package com.hubspot.slack.client.models.dialog.form.elements;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Strings;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.dialog.form.elements.helpers.SlackDialogElementNormalizer;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface SlackFormOptionGroupIF extends HasLabel, HasOptions {
  @Check
  default SlackFormOptionGroupIF validate() {
    SlackFormOptionGroupIF normalized = SlackDialogElementNormalizer.normalize(this);
    String label = normalized.getLabel();
    int numOptions = normalized.getOptions().size();

    if (Strings.isNullOrEmpty(label)) {
      throw new IllegalStateException("Must provide a label");
    }

    int maxOptionLabelLength = SlackDialogFormElementLengthLimits.MAX_OPTION_LABEL_LENGTH.getLimit();
    if (label.length() > maxOptionLabelLength) {
      String errorMessage = String.format(
        "Label cannot exceed %s chars - '%s'",
        maxOptionLabelLength,
        label
      );
      throw new IllegalStateException(errorMessage);
    }

    int maxOptionsNumber = SlackDialogFormElementLengthLimits.MAX_OPTIONS_NUMBER.getLimit();
    if (numOptions > maxOptionsNumber) {
      String errorMessage = String.format(
        "Cannot have more than %s option groups. Has %s",
        maxOptionsNumber,
        numOptions
      );
      throw new IllegalStateException(errorMessage);
    }

    return normalized;
  }
}

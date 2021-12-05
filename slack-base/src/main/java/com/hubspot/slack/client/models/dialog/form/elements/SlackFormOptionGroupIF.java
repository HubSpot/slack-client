package com.hubspot.slack.client.models.dialog.form.elements;

import java.util.List;

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
public interface SlackFormOptionGroupIF extends HasLabel{
  List<SlackFormOption> getOptions();

  @Check
  default SlackFormOptionGroupIF validate() {
    SlackFormOptionGroupIF normalized = SlackDialogElementNormalizer.normalize(this);
    String label = normalized.getLabel();
    int numOptions = normalized.getOptions().size();

    if (Strings.isNullOrEmpty(label)) {
      throw new IllegalStateException("Must provide a label");
    }

    if (label.length() > SlackDialogFormElementLengthLimits.MAX_OPTION_LABEL_LENGTH.getLimit()) {
      throw new IllegalStateException("Label cannot exceed 75 chars - '" + label + "'");
    }

    if (numOptions > SlackDialogFormElementLengthLimits.MAX_OPTIONS_NUMBER.getLimit()) {
      throw new IllegalStateException("Cannot have more than 100 option groups. Has " + numOptions);
    }

    return normalized;
  }
}

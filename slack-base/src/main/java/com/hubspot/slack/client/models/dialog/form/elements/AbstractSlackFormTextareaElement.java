package com.hubspot.slack.client.models.dialog.form.elements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.dialog.form.SlackFormElementTypes;
import com.hubspot.slack.client.models.dialog.form.elements.helpers.SlackDialogElementNormalizer;
import java.util.Optional;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(Include.NON_EMPTY)
public abstract class AbstractSlackFormTextareaElement
  extends AbstractSlackDialogFormTextElement {

  @Default
  @Override
  public SlackFormElementTypes getType() {
    return SlackFormElementTypes.TEXTAREA;
  }

  @Check
  public AbstractSlackFormTextareaElement validate() {
    AbstractSlackFormTextareaElement normalized = SlackDialogElementNormalizer.normalize(
      this
    );
    super.validateBaseTextElementProps(normalized);

    int normalizedMaxLength = normalized.getMaxLength();
    int maxTextareaElementValueLengthLimit =
      SlackDialogFormElementLengthLimits.MAX_TEXT_AREA_ELEMENT_VALUE_LENGTH.getLimit();
    if (normalizedMaxLength > maxTextareaElementValueLengthLimit) {
      String errorMessage = String.format(
        "Form text area cannot have max length > %s chars, got %s",
        maxTextareaElementValueLengthLimit,
        normalizedMaxLength
      );
      throw new IllegalStateException(errorMessage);
    }

    int normalizedMinLength = normalized.getMinLength();
    if (normalizedMinLength > maxTextareaElementValueLengthLimit) {
      String errorMessage = String.format(
        "Form text area cannot have min length > %s chars, got %s",
        maxTextareaElementValueLengthLimit,
        normalizedMinLength
      );
      throw new IllegalStateException(errorMessage);
    }

    Optional<String> value = normalized.getValue();
    if (value.isPresent() && value.get().length() > maxTextareaElementValueLengthLimit) {
      String errorMessage = String.format(
        "Value cannot exceed %s chars, got %s",
        maxTextareaElementValueLengthLimit,
        value.get()
      );
      throw new IllegalStateException(errorMessage);
    }
    return normalized;
  }
}

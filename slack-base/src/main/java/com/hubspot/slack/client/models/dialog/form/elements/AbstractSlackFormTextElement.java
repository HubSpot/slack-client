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
public abstract class AbstractSlackFormTextElement
  extends AbstractSlackDialogFormTextElement {

  @Default
  @Override
  public SlackFormElementTypes getType() {
    return SlackFormElementTypes.TEXT;
  }

  @Check
  public AbstractSlackFormTextElement validate() {
    AbstractSlackFormTextElement normalized = SlackDialogElementNormalizer.normalize(
      this
    );
    super.validateBaseTextElementProps(normalized);

    int normalizedMaxLength = normalized.getMaxLength();
    int maxTextElementValueLength = SlackDialogFormElementLengthLimits.MAX_TEXT_ELEMENT_VALUE_LENGTH.getLimit();
    if (normalizedMaxLength > maxTextElementValueLength) {
      String errorMessage = String.format(
        "Form text element cannot have max length > %s chars, got %s",
        maxTextElementValueLength,
        normalizedMaxLength
      );
      throw new IllegalStateException(errorMessage);
    }

    int normalizedMinLength = normalized.getMinLength();
    if (normalizedMinLength > maxTextElementValueLength) {
      String errorMessage = String.format(
        "Form text element cannot have min length > %s chars, got %s",
        maxTextElementValueLength,
        normalizedMinLength
      );
      throw new IllegalStateException(errorMessage);
    }

    Optional<String> value = normalized.getValue();
    if (value.isPresent() && value.get().length() > maxTextElementValueLength) {
      String errorMessage = String.format(
        "Value cannot exceed %s chars, got %s",
        maxTextElementValueLength,
        value.get()
      );
      throw new IllegalStateException(errorMessage);
    }
    return normalized;
  }
}

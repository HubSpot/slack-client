package com.hubspot.slack.client.models.dialog.form.elements;

import java.util.Optional;

import org.immutables.value.Value.Check;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.dialog.form.SlackFormElementTypes;
import com.hubspot.slack.client.models.dialog.form.elements.helper.SlackDialogElementNormalizer;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(Include.NON_EMPTY)
public abstract class AbstractSlackFormTextareaElement extends AbstractSlackDialogFormTextElement {
  @Default
  @Override
  public SlackFormElementTypes getType() {
    return SlackFormElementTypes.TEXTAREA;
  }

  @Check
  public AbstractSlackFormTextareaElement validate() {
    AbstractSlackFormTextareaElement normalized = SlackDialogElementNormalizer.normalize(this);
    super.validateBaseTextElementProps(normalized);

    int normalizedMaxLength = normalized.getMaxLength();
    if (normalizedMaxLength > SlackDialogFormElementLengthLimits.MAX_TEXT_ELEMENT_VALUE_LENGTH.getLimit()) {
      throw new IllegalStateException("Form text area cannot have max length > 3000 chars, got " + normalizedMaxLength);
    }

    int normalizedMinLength = normalized.getMinLength();
    if (normalizedMinLength > SlackDialogFormElementLengthLimits.MAX_TEXT_ELEMENT_VALUE_LENGTH.getLimit()) {
      throw new IllegalStateException("Form text area cannot have min length > 3000 chars, got " + normalizedMinLength);
    }

    Optional<String> value = normalized.getValue();
    if (value.isPresent() && value.get().length() > 3000) {
      throw new IllegalStateException("Value cannot exceed 3000 chars, got '" + value.get() + "'");
    }
    return normalized;
  }
}

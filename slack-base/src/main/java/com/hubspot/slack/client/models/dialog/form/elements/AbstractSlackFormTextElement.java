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
public abstract class AbstractSlackFormTextElement extends AbstractSlackDialogFormTextElement {
  @Default
  @Override
  public SlackFormElementTypes getType() {
    return SlackFormElementTypes.TEXT;
  }

  @Check
  public AbstractSlackFormTextElement validate() {
    AbstractSlackFormTextElement normalized = SlackDialogElementNormalizer.normalize(this);
    super.validateBaseTextElementProps(normalized);

    int normalizedMaxLength = normalized.getMaxLength();
    if (normalizedMaxLength > SlackDialogFormElementLengthLimits.MAX_TEXT_ELEMENT_VALUE_LENGTH.getLimit()) {
      throw new IllegalStateException("Form text element cannot have max length > 150 chars, got " + normalizedMaxLength);
    }

    int normalizedMinLength = normalized.getMinLength();
    if (normalizedMinLength > SlackDialogFormElementLengthLimits.MAX_TEXT_ELEMENT_VALUE_LENGTH.getLimit()) {
      throw new IllegalStateException("Form text element cannot have min length > 150 chars, got " + normalizedMinLength);
    }

    Optional<String> value = normalized.getValue();
    if (value.isPresent() && value.get().length() > SlackDialogFormElementLengthLimits.MAX_TEXT_ELEMENT_VALUE_LENGTH.getLimit()) {
      throw new IllegalStateException("Value cannot exceed 150 chars, got '" + value.get() + "'");
    }
    return normalized;
  }
}

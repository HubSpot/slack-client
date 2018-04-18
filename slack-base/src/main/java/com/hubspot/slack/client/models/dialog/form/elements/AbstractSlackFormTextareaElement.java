package com.hubspot.slack.client.models.dialog.form.elements;

import org.immutables.value.Value.Check;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.dialog.form.SlackFormElementTypes;

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
  public void validate() {
    super.validateBaseTextElementProps();

    if (getMaxLength() > 3000) {
      throw new IllegalStateException("Form text area cannot have max length > 500 chars, got " + getMaxLength());
    }
  }
}

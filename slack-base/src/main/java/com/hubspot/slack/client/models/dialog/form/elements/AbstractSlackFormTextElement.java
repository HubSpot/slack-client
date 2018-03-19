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
public abstract class AbstractSlackFormTextElement extends AbstractSlackDialogFormTextElement {
  @Default
  @Override
  public SlackFormElementTypes getType() {
    return SlackFormElementTypes.TEXT;
  }

  @Check
  public void validate() {
    super.validateBaseTextElementProps();

    if (getMaxLength() > 150) {
      throw new IllegalStateException("Form text element cannot have max length > 150 chars, got " + getMaxLength());
    }
  }
}

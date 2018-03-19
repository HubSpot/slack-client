package com.hubspot.slack.client.models.dialog.form.elements;

import java.util.List;

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
public abstract class AbstractSlackFormSelectElement extends SlackDialogFormElement {
  @Default
  @Override
  public SlackFormElementTypes getType() {
    return SlackFormElementTypes.SELECT;
  }

  public abstract List<SlackFormOption> getOptions();
  public abstract String getValue();

  @Check
  public void validate() {
    super.validateBaseElementProperties();

    if (getOptions().size() > 100) {
      throw new IllegalStateException("Cannot have more than 100 options");
    }

    String value = getValue();
    boolean valueIsSomeOptionValue = getOptions().stream()
        .anyMatch(option -> option.getValue().equalsIgnoreCase(value));
    if (!valueIsSomeOptionValue) {
      throw new IllegalStateException("Value must exactly match the value field for one provided option");
    }
  }
}

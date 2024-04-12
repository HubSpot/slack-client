package com.hubspot.slack.client.models.interaction.views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Optional;

@JsonTypeInfo(
  use = Id.NAME,
  include = As.EXISTING_PROPERTY,
  property = "type",
  visible = true
)
@JsonNaming(SnakeCaseStrategy.class)
@JsonSubTypes(
  {
    @Type(value = ViewPlainTextInput.class, name = "plain_text_input"),
    @Type(value = ViewNumberInput.class, name = "number_input"),
    @Type(value = ViewEmailInput.class, name = "email_text_input"),
    @Type(value = ViewUrlInput.class, name = "url_text_input"),
    @Type(value = ViewDatePicker.class, name = "datepicker"),
    @Type(value = ViewTimePicker.class, name = "timepicker"),
    @Type(value = ViewDateTimePicker.class, name = "datetimepicker"),
    @Type(value = ViewRadioButtonGroup.class, name = "radio_buttons"),
    @Type(value = UsersSelectInput.class, name = "users_select"),
    @Type(value = ViewCheckboxes.class, name = "checkboxes"),
    @Type(value = ViewConversationsSelect.class, name = "conversations_select"),
    @Type(value = ViewStaticSelect.class, name = "static_select"),
    @Type(value = ViewExternalSelect.class, name = "external_select"),
    @Type(value = ViewMultiStaticSelect.class, name = "multi_static_select"),
    @Type(value = ViewMultiExternalSelect.class, name = "multi_external_select"),
  }
)
public interface ViewInput {
  ViewInputType getType();

  @JsonIgnore
  Optional<String> getStringValue();
}

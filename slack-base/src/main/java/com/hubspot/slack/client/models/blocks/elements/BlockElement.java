package com.hubspot.slack.client.models.blocks.elements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = UnknownBlockElement.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Button.class, name = Button.TYPE),
    @JsonSubTypes.Type(value = DatePicker.class, name = DatePicker.TYPE),
    @JsonSubTypes.Type(value = TimePicker.class, name = TimePicker.TYPE),
    @JsonSubTypes.Type(value = ChannelSelectMenu.class, name = ChannelSelectMenu.TYPE),
    @JsonSubTypes.Type(value = ChannelsMultiSelectMenu.class, name = ChannelsMultiSelectMenu.TYPE),
    @JsonSubTypes.Type(value = Checkboxes.class, name = Checkboxes.TYPE),
    @JsonSubTypes.Type(value = ConversationSelectMenu.class, name = ConversationSelectMenu.TYPE),
    @JsonSubTypes.Type(value = ConversationsMultiSelectMenu.class, name = ConversationsMultiSelectMenu.TYPE),
    @JsonSubTypes.Type(value = ExternalMultiSelectMenu.class, name = ExternalMultiSelectMenu.TYPE),
    @JsonSubTypes.Type(value = ExternalSelectMenu.class, name = ExternalSelectMenu.TYPE),
    @JsonSubTypes.Type(value = Image.class, name = Image.TYPE),
    @JsonSubTypes.Type(value = OverflowMenu.class, name = OverflowMenu.TYPE),
    @JsonSubTypes.Type(value = PlainTextInput.class, name = PlainTextInput.TYPE),
    @JsonSubTypes.Type(value = RadioButtonGroup.class, name = RadioButtonGroup.TYPE),
    @JsonSubTypes.Type(value = StaticMultiSelectMenu.class, name = StaticMultiSelectMenu.TYPE),
    @JsonSubTypes.Type(value = StaticSelectMenu.class, name = StaticSelectMenu.TYPE),
    @JsonSubTypes.Type(value = UserSelectMenu.class, name = UserSelectMenu.TYPE),
    @JsonSubTypes.Type(value = UsersMultiSelectMenu.class, name = UsersMultiSelectMenu.TYPE),
})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface BlockElement {
  String getType();
}

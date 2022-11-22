package com.hubspot.slack.client.models.blocks;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hubspot.slack.client.models.BlockOrAttachment;
import com.hubspot.slack.client.models.blocks.messages.Emoji;
import com.hubspot.slack.client.models.blocks.messages.Link;
import com.hubspot.slack.client.models.blocks.messages.RichText;
import com.hubspot.slack.client.models.blocks.messages.RichTextList;
import com.hubspot.slack.client.models.blocks.messages.RichTextPreformatted;
import com.hubspot.slack.client.models.blocks.messages.RichTextQuote;
import com.hubspot.slack.client.models.blocks.messages.RichTextSection;
import com.hubspot.slack.client.models.blocks.messages.Text;
import com.hubspot.slack.client.models.blocks.messages.User;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = UnknownBlock.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Actions.class, name = Actions.TYPE),
    @JsonSubTypes.Type(value = Context.class, name = Context.TYPE),
    @JsonSubTypes.Type(value = Divider.class, name = Divider.TYPE),
    @JsonSubTypes.Type(value = File.class, name = File.TYPE),
    @JsonSubTypes.Type(value = Image.class, name = Image.TYPE),
    @JsonSubTypes.Type(value = Input.class, name = Input.TYPE),
    @JsonSubTypes.Type(value = Section.class, name = Section.TYPE),
    @JsonSubTypes.Type(value = Header.class, name = Header.TYPE),
    @JsonSubTypes.Type(value = Text.class, name = Text.TYPE),
    @JsonSubTypes.Type(value = Link.class, name = Link.TYPE),
    @JsonSubTypes.Type(value = User.class, name = User.TYPE),
    @JsonSubTypes.Type(value = Emoji.class, name = Emoji.TYPE),
    @JsonSubTypes.Type(value = RichText.class, name = RichText.TYPE),
    @JsonSubTypes.Type(value = RichTextSection.class, name = RichTextSection.TYPE),
    @JsonSubTypes.Type(value = RichTextList.class, name = RichTextList.TYPE),
    @JsonSubTypes.Type(value = RichTextQuote.class, name = RichTextQuote.TYPE),
    @JsonSubTypes.Type(value = RichTextPreformatted.class, name = RichTextPreformatted.TYPE)
})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface Block extends BlockOrAttachment {
  String getType();

  Optional<String> getBlockId();
}

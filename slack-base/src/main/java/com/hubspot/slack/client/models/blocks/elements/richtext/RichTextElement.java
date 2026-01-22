package com.hubspot.slack.client.models.blocks.elements.richtext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hubspot.slack.client.models.blocks.UnknownBlock;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  property = "type",
  defaultImpl = UnknownBlock.class
)
@JsonSubTypes(
  {
    @JsonSubTypes.Type(value = RichTextBroadcast.class, name = RichTextBroadcast.TYPE),
    @JsonSubTypes.Type(value = RichTextColor.class, name = RichTextColor.TYPE),
    @JsonSubTypes.Type(value = RichTextChannel.class, name = RichTextChannel.TYPE),
    @JsonSubTypes.Type(value = RichTextDate.class, name = RichTextDate.TYPE),
    @JsonSubTypes.Type(value = RichTextEmoji.class, name = RichTextEmoji.TYPE),
    @JsonSubTypes.Type(value = RichTextLink.class, name = RichTextLink.TYPE),
    @JsonSubTypes.Type(value = RichTextText.class, name = RichTextText.TYPE),
    @JsonSubTypes.Type(value = RichTextUser.class, name = RichTextUser.TYPE),
    @JsonSubTypes.Type(value = RichTextUserGroup.class, name = RichTextUserGroup.TYPE),
  }
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface RichTextElement extends RichTextBlock {}

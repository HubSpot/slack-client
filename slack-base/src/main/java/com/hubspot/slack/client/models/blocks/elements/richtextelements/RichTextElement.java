package com.hubspot.slack.client.models.blocks.elements.richtextelements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hubspot.slack.client.models.blocks.elements.BlockElement;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
  {
    @JsonSubTypes.Type(
      value = RichTextTextElement.class,
      name = RichTextTextElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextLinkElement.class,
      name = RichTextLinkElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextChannelElement.class,
      name = RichTextChannelElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextUserElement.class,
      name = RichTextUserElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextUserGroupElement.class,
      name = RichTextUserGroupElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextBroadcastElement.class,
      name = RichTextBroadcastElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextColorElement.class,
      name = RichTextColorElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextDateElement.class,
      name = RichTextDateElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextEmojiElement.class,
      name = RichTextEmojiElement.TYPE
    ),
  }
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface RichTextElement extends BlockElement {}

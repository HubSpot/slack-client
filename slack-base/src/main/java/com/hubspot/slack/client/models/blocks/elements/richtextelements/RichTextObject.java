package com.hubspot.slack.client.models.blocks.elements.richtextelements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hubspot.slack.client.models.blocks.elements.BlockElement;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
  {
    @JsonSubTypes.Type(value = RichTextSection.class, name = RichTextSection.TYPE),
    @JsonSubTypes.Type(value = RichTextList.class, name = RichTextList.TYPE),
    @JsonSubTypes.Type(
      value = RichTextPreformatted.class,
      name = RichTextPreformatted.TYPE
    ),
    @JsonSubTypes.Type(value = RichTextQuote.class, name = RichTextQuote.TYPE),
  }
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface RichTextObject extends BlockElement {}

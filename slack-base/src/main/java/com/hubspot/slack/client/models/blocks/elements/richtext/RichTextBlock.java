package com.hubspot.slack.client.models.blocks.elements.richtext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hubspot.slack.client.models.blocks.Block;
import com.hubspot.slack.client.models.blocks.UnknownBlock;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  property = "type",
  defaultImpl = UnknownBlock.class
)
@JsonSubTypes(
  {
    @JsonSubTypes.Type(value = RichTextList.class, name = RichTextList.TYPE),
    @JsonSubTypes.Type(
      value = RichTextPreformatted.class,
      name = RichTextPreformatted.TYPE
    ),
    @JsonSubTypes.Type(value = RichTextQuote.class, name = RichTextQuote.TYPE),
    @JsonSubTypes.Type(value = RichTextSection.class, name = RichTextSection.TYPE),
  }
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface RichTextBlock extends Block {}

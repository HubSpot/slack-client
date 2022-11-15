package com.hubspot.slack.client.models.blocks.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hubspot.slack.client.models.blocks.Block;
//import com.fasterxml.jackson.annotation.JsonSubTypes;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = Text.class, name = Text.TYPE),
//        @JsonSubTypes.Type(value = Link.class, name = Link.TYPE),
//        @JsonSubTypes.Type(value = RichText.class, name = RichText.TYPE),
//        @JsonSubTypes.Type(value = RichTextSection.class, name = RichTextSection.TYPE),
//        @JsonSubTypes.Type(value = RichTextList.class, name = RichTextList.TYPE),
//        @JsonSubTypes.Type(value = RichTextQuote.class, name = RichTextQuote.TYPE),
//        @JsonSubTypes.Type(value = RichTextPreformatted.class, name = RichTextPreformatted.TYPE)
//})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface MessageBlock extends Block {
}

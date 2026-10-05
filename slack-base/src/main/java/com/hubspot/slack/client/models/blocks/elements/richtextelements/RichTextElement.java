package com.hubspot.slack.client.models.blocks.elements.richtextelements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hubspot.slack.client.models.blocks.elements.BlockElement;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  property = "type",
  defaultImpl = UnknownRichTextElement.class
)
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
    @JsonSubTypes.Type(
      value = RichTextAttachmentMentionElement.class,
      name = RichTextAttachmentMentionElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextTeamElement.class,
      name = RichTextTeamElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextMessageMentionElement.class,
      name = RichTextMessageMentionElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextFileElement.class,
      name = RichTextFileElement.TYPE
    ),
    @JsonSubTypes.Type(value = RichTextTagElement.class, name = RichTextTagElement.TYPE),
    @JsonSubTypes.Type(
      value = RichTextListRecordElement.class,
      name = RichTextListRecordElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextWorkObjectMentionElement.class,
      name = RichTextWorkObjectMentionElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextWorkflowMentionElement.class,
      name = RichTextWorkflowMentionElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextSalesforceDataFieldElement.class,
      name = RichTextSalesforceDataFieldElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextCanvasElement.class,
      name = RichTextCanvasElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextCanvasUserMentionElement.class,
      name = RichTextCanvasUserMentionElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextCanvasMessageUnfurlElement.class,
      name = RichTextCanvasMessageUnfurlElement.TYPE
    ),
    @JsonSubTypes.Type(
      value = RichTextCitationElement.class,
      name = RichTextCitationElement.TYPE
    ),
  }
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface RichTextElement extends BlockElement {}

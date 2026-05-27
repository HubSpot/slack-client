package com.hubspot.slack.client.models.blocks.elements.contextactionselements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hubspot.slack.client.models.blocks.elements.BlockElement;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  property = "type",
  defaultImpl = UnknownContextActionsElement.class
)
@JsonSubTypes(
  {
    @JsonSubTypes.Type(
      value = FeedbackButtonsElement.class,
      name = FeedbackButtonsElement.TYPE
    ),
    @JsonSubTypes.Type(value = IconButtonElement.class, name = IconButtonElement.TYPE),
  }
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface ContextActionsElement extends BlockElement {}

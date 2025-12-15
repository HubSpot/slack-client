package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(
  use = Id.NAME,
  include = As.EXISTING_PROPERTY,
  property = "type",
  visible = true
)
@JsonSubTypes(
  {
    @Type(value = ViewContainer.class, name = "view"),
    @Type(value = MessageContainer.class, name = "message"),
    @Type(value = MessageAttachmentContainer.class, name = "message_attachment"),
    @Type(value = EntityDetailContainer.class, name = "entity_detail"),
  }
)
public interface Container {
  ContainerType getType();
}

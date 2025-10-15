package com.hubspot.slack.client.methods.params.chat.workobject;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hubspot.slack.client.methods.params.chat.workobject.entity.EntityPayload;
import com.hubspot.slack.client.methods.params.chat.workobject.serializers.EntityTypeDeserializer;
import com.hubspot.slack.client.methods.params.chat.workobject.serializers.EntityTypeSerializer;

public interface WorkObjectBase {
  @JsonSerialize(using = EntityTypeSerializer.class)
  @JsonDeserialize(using = EntityTypeDeserializer.class)
  EntityType getEntityType();

  EntityPayload getEntityPayload();

  String getUrl();

  ExternalRef getExternalRef();
}

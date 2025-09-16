package com.hubspot.slack.client.methods.params.chat.workobject.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hubspot.slack.client.methods.params.chat.workobject.EntityType;
import java.io.IOException;

public class EntityTypeSerializer extends JsonSerializer<EntityType> {

  @Override
  public void serialize(
    EntityType entityType,
    JsonGenerator gen,
    SerializerProvider serializers
  ) throws IOException {
    gen.writeString(entityType.getValue());
  }
}

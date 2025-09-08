package com.hubspot.slack.client.methods.params.chat.workobject.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hubspot.slack.client.methods.params.chat.workobject.EntityType;
import java.io.IOException;
import java.util.Optional;

public class EntityTypeSerializer extends JsonSerializer<Optional<EntityType>> {

  @Override
  public void serialize(
    Optional<EntityType> entityType,
    JsonGenerator gen,
    SerializerProvider serializers
  ) throws IOException {
    if (entityType.isPresent()) {
      gen.writeString(entityType.get().getValue());
    } else {
      gen.writeNull();
    }
  }
}

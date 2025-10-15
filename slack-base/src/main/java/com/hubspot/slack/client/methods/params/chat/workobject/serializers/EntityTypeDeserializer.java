package com.hubspot.slack.client.methods.params.chat.workobject.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.hubspot.slack.client.methods.params.chat.workobject.EntityType;
import java.io.IOException;
import java.util.Arrays;

public class EntityTypeDeserializer extends JsonDeserializer<EntityType> {

  @Override
  public EntityType deserialize(JsonParser p, DeserializationContext ctxt)
    throws IOException {
    String value = p.getText();
    return Arrays
      .stream(EntityType.values())
      .filter(type -> type.getValue().equals(value))
      .findFirst()
      .orElseThrow(() ->
        new IllegalArgumentException("Unknown EntityType value: " + value)
      );
  }
}

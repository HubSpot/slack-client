package com.hubspot.slack.client.methods.params.chat.workobject.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.hubspot.slack.client.methods.params.chat.workobject.entity.FieldDataType;
import java.io.IOException;
import java.util.Arrays;

public class FieldDataTypeDeserializer extends JsonDeserializer<FieldDataType> {

  @Override
  public FieldDataType deserialize(JsonParser p, DeserializationContext ctxt)
    throws IOException {
    String value = p.getText();
    return Arrays
      .stream(FieldDataType.values())
      .filter(type -> type.getValue().equals(value))
      .findFirst()
      .orElseThrow(() ->
        new IllegalArgumentException("Unknown FieldDataType value: " + value)
      );
  }
}

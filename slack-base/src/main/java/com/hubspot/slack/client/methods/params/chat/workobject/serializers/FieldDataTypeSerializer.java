package com.hubspot.slack.client.methods.params.chat.workobject.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hubspot.slack.client.methods.params.chat.workobject.entity.FieldDataType;
import java.io.IOException;

public class FieldDataTypeSerializer extends JsonSerializer<FieldDataType> {

  @Override
  public void serialize(
    FieldDataType fieldDataType,
    JsonGenerator jsonGenerator,
    SerializerProvider serializerProvider
  ) throws IOException {
    jsonGenerator.writeString(fieldDataType.getValue());
  }
}

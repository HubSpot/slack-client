package com.hubspot.slack.client.methods.params.chat.workobject.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.hubspot.slack.client.methods.params.chat.workobject.entity.DisplayType;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class DisplayTypeDeserializer extends JsonDeserializer<Optional<DisplayType>> {

  @Override
  public Optional<DisplayType> deserialize(JsonParser p, DeserializationContext ctxt)
    throws IOException {
    String value = p.getText();
    if (value == null || value.isEmpty()) {
      return Optional.empty();
    }
    return Arrays
      .stream(DisplayType.values())
      .filter(type -> type.getValue().equals(value))
      .findFirst();
  }

  @Override
  public Optional<DisplayType> getNullValue(DeserializationContext ctxt) {
    return Optional.empty();
  }
}

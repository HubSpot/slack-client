package com.hubspot.slack.client.methods.params.chat.workobject.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.hubspot.slack.client.methods.params.chat.workobject.entity.fields.TagColor;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class TagColorDeserializer extends JsonDeserializer<Optional<TagColor>> {

  @Override
  public Optional<TagColor> deserialize(JsonParser p, DeserializationContext ctxt)
    throws IOException {
    String value = p.getText();
    if (value == null || value.isEmpty()) {
      return Optional.empty();
    }
    return Arrays
      .stream(TagColor.values())
      .filter(type -> type.getValue().equals(value))
      .findFirst();
  }

  @Override
  public Optional<TagColor> getNullValue(DeserializationContext ctxt) {
    return Optional.empty();
  }
}

package com.hubspot.slack.client.methods.params.chat.workobject.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hubspot.slack.client.methods.params.chat.workobject.entity.fields.TagColor;
import java.io.IOException;
import java.util.Optional;

public class TagColorSerializer extends JsonSerializer<Optional<TagColor>> {

  @Override
  public void serialize(
    Optional<TagColor> tagColorMaybe,
    JsonGenerator gen,
    SerializerProvider serializers
  ) throws IOException {
    if (tagColorMaybe.isPresent()) {
      gen.writeString(tagColorMaybe.get().getValue());
    } else {
      gen.writeNull();
    }
  }

  @Override
  public boolean isEmpty(SerializerProvider provider, Optional<TagColor> value) {
    return value.isEmpty();
  }
}

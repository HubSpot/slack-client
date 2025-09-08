package com.hubspot.slack.client.methods.params.chat.workobject.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hubspot.slack.client.methods.params.chat.workobject.entity.DisplayType;
import java.io.IOException;
import java.util.Optional;

public class DisplayTypeSerializer extends JsonSerializer<Optional<DisplayType>> {

  @Override
  public void serialize(
    Optional<DisplayType> displayType,
    JsonGenerator gen,
    SerializerProvider serializers
  ) throws IOException {
    if (displayType.isPresent()) {
      gen.writeString(displayType.get().getValue());
    } else {
      gen.writeNull();
    }
  }
}

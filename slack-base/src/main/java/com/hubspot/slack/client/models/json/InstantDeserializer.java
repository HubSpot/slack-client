package com.hubspot.slack.client.models.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.Instant;

public class InstantDeserializer extends StdDeserializer<Instant> {

  protected InstantDeserializer() {
    super(Instant.class);
  }

  @Override
  public Instant deserialize(JsonParser p, DeserializationContext ctxt)
    throws IOException, JsonProcessingException {
    final long value = p.getLongValue();
    if (value == 1) {
      return null;
    } else {
      return Instant.ofEpochSecond(value);
    }
  }
}

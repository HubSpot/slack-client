package com.hubspot.slack.client.models.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.Instant;

public class InstantSerializer extends StdSerializer<Instant>  {

  protected InstantSerializer() {
    super(Instant.class);
  }

  @Override
  public void serialize(Instant value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeNumber(value.getEpochSecond());
  }
}

package com.hubspot.slack.client.models.response.views.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.hubspot.slack.client.models.response.views.StateBlock;
import java.io.IOException;

public class StateBlockSerializer extends StdSerializer<StateBlock> {

  private static final String VALUES_FIELD = "values";

  protected StateBlockSerializer() {
    super(StateBlock.class);
  }

  @Override
  public void serialize(StateBlock value, JsonGenerator gen, SerializerProvider provider)
    throws IOException {
    gen.writeStartObject();
    gen.writeObjectField(VALUES_FIELD, value);
    gen.writeEndObject();
  }
}

package com.hubspot.slack.client.models.response.views.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.hubspot.slack.client.models.response.views.StateActionValue;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class StateActionValueSerializer extends StdSerializer<StateActionValue> {
  protected StateActionValueSerializer() {
    super(StateActionValue.class);
  }

  @Override
  public void serialize(StateActionValue stateActionValue, JsonGenerator gen, SerializerProvider provider) throws IOException {
    final String type = stateActionValue.getBlockElementType();
    final Optional<Object> value = stateActionValue.getBlockElementValue();

    gen.writeStartObject();
    gen.writeStringField("type", type);

    if ("datepicker".equals(type)) {
      gen.writeStringField("selected_date", value.map(Object::toString).orElse(null));
    } else if ("radio_buttons".equals(type) || "external_select".equals(type)) {
      gen.writeObjectField("selected_option", value.orElse(null));
    } else {
      gen.writeStringField("value", value.map(Object::toString).orElse(null));
    }
    gen.writeEndObject();
  }
}

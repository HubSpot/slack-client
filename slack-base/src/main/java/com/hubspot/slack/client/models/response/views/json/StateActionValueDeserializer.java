package com.hubspot.slack.client.models.response.views.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hubspot.slack.client.models.blocks.objects.Option;
import com.hubspot.slack.client.models.response.views.StateActionValue;

import java.io.IOException;
import java.time.LocalDate;

public class StateActionValueDeserializer extends StdDeserializer<StateActionValue> {
  private static final String DATE_FIELD = "selected_date";

  protected StateActionValueDeserializer() {
    super(StateActionValue.class);
  }

  @Override
  public StateActionValue deserialize(JsonParser p, DeserializationContext context) throws IOException {
    final StateActionValue.Builder builder = StateActionValue.builder();

    ObjectCodec codec = p.getCodec();
    JsonNode node = codec.readTree(p);

    if (node.has("type")) {
      builder.setBlockElementType(node.get("type").asText());
    }

    if (node.has("value")) {
      builder.setBlockElementValue(node.get("value").asText());
    } else if (node.has("selected_option")) {
      final JsonNode selectedOption = node.get("selected_option");
      final Option option = codec.treeToValue(selectedOption, Option.class);
      if (option != null) {
        builder.setBlockElementValue(option);
      }
    } else if (node.has("selected_date")) {
      builder.setBlockElementValue(LocalDate.parse(node.get("selected_date").asText()));
    }

    return builder.build();
  }

}

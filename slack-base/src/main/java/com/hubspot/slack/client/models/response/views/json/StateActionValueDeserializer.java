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
  private static final String BLOCK_ELEMENT_TYPE = "type";
  private static final String BLOCK_ELEMENT_VALUE = "value";
  private static final String SELECTED_OPTION = "selected_option";
  private static final String DATE_FIELD = "selected_date";

  protected StateActionValueDeserializer() {
    super(StateActionValue.class);
  }

  @Override
  public StateActionValue deserialize(JsonParser p, DeserializationContext context) throws IOException {
    final StateActionValue.Builder builder = StateActionValue.builder();

    ObjectCodec codec = p.getCodec();
    JsonNode node = codec.readTree(p);

    if (node.has(BLOCK_ELEMENT_TYPE)) {
      builder.setBlockElementType(node.get(BLOCK_ELEMENT_TYPE).asText());
    }

    if (node.has(BLOCK_ELEMENT_VALUE)) {
      builder.setBlockElementValue(node.get(BLOCK_ELEMENT_VALUE).asText());
    } else if (node.has(SELECTED_OPTION)) {
      final JsonNode selectedOption = node.get(SELECTED_OPTION);
      final Option option = codec.treeToValue(selectedOption, Option.class);
      if (option != null) {
        builder.setBlockElementValue(option);
      }
    } else if (node.has(DATE_FIELD)) {
      if (!node.get(DATE_FIELD).isNull()) {
        builder.setBlockElementValue(LocalDate.parse(node.get(DATE_FIELD).asText()));
      }
    }

    return builder.build();
  }

}

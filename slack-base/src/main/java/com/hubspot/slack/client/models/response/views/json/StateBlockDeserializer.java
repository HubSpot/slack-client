package com.hubspot.slack.client.models.response.views.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hubspot.slack.client.models.response.views.StateBlock;

public class StateBlockDeserializer extends StdDeserializer<StateBlock> {

  private static final String VALUES_FIELD = "values";

  protected StateBlockDeserializer() {
    super(StateBlock.class);
  }

  @Override
  public StateBlock deserialize(JsonParser p, DeserializationContext context) throws IOException {
    ObjectCodec codec = p.getCodec();
    JsonNode node = codec.readTree(p);
    return codec.treeToValue(node.get(VALUES_FIELD), StateBlock.class);
  }
}

package com.hubspot.slack.client.models.events.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hubspot.slack.client.models.events.SlackEvent;
import com.hubspot.slack.client.models.events.SlackEventType;
import com.hubspot.slack.client.models.events.SlackMessageSubtype;

public class EventDeserializer extends StdDeserializer<SlackEvent> {
  private static final String TYPE_FIELD = "type";
  private static final String SUBTYPE_FIELD = "subtype";

  public EventDeserializer() {
    super(SlackEvent.class);
  }

  @Override
  public SlackEvent deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    ObjectCodec codec = p.getCodec();
    JsonNode node = codec.readTree(p);

    SlackEventType type = SlackEventType.get(node.get(TYPE_FIELD).asText());

    // Messages can have subtypes that we need to handle
    if (type == SlackEventType.MESSAGE && node.has(SUBTYPE_FIELD)) {
      SlackMessageSubtype subtype = SlackMessageSubtype.get(node.get(SUBTYPE_FIELD).asText());
      return codec.treeToValue(node, subtype.getMessageClass());
    }

    return codec.treeToValue(node, type.getEventClass());
  }
}

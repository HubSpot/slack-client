package com.hubspot.slack.client.models.blocks.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hubspot.slack.client.models.blocks.objects.Option;
import com.hubspot.slack.client.models.blocks.objects.OptionGroup;
import com.hubspot.slack.client.models.blocks.objects.OptionOrOptionGroup;
import java.io.IOException;

public class OptionOrOptionGroupDeserializer
  extends StdDeserializer<OptionOrOptionGroup> {

  private static final String OPTIONS_FIELD = "options";

  protected OptionOrOptionGroupDeserializer() {
    super(OptionOrOptionGroup.class);
  }

  @Override
  public OptionOrOptionGroup deserialize(JsonParser p, DeserializationContext context)
    throws IOException {
    ObjectCodec codec = p.getCodec();
    JsonNode node = codec.readTree(p);

    if (node.has(OPTIONS_FIELD)) {
      return codec.treeToValue(node, OptionGroup.class);
    }

    return codec.treeToValue(node, Option.class);
  }
}

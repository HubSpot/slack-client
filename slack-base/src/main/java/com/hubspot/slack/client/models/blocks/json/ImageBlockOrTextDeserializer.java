package com.hubspot.slack.client.models.blocks.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hubspot.slack.client.models.blocks.Image;
import com.hubspot.slack.client.models.blocks.objects.ImageBlockOrText;
import com.hubspot.slack.client.models.blocks.objects.Text;

public class ImageBlockOrTextDeserializer extends StdDeserializer<ImageBlockOrText> {
  private static final String TYPE_FIELD = "type";

  protected ImageBlockOrTextDeserializer() {
    super(ImageBlockOrText.class);
  }

  @Override
  public ImageBlockOrText deserialize(JsonParser p, DeserializationContext context) throws IOException {
    ObjectCodec codec = p.getCodec();
    JsonNode node = codec.readTree(p);

    String type = node.get(TYPE_FIELD).asText();
    if (Image.TYPE.equals(type)) {
      return codec.treeToValue(node, Image.class);
    }

    return codec.treeToValue(node, Text.class);
  }
}

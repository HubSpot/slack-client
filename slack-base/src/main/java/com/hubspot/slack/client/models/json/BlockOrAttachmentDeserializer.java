package com.hubspot.slack.client.models.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hubspot.slack.client.models.Attachment;
import com.hubspot.slack.client.models.BlockOrAttachment;
import com.hubspot.slack.client.models.blocks.Block;

public class BlockOrAttachmentDeserializer extends StdDeserializer<BlockOrAttachment> {
  private static final String TYPE_FIELD = "type";

  protected BlockOrAttachmentDeserializer() {
    super(BlockOrAttachment.class);
  }

  @Override
  public BlockOrAttachment deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
    ObjectCodec codec = jsonParser.getCodec();
    JsonNode node = codec.readTree(jsonParser);

    if (node.has(TYPE_FIELD)) {
      return codec.treeToValue(node, Block.class);
    } else {
      return codec.treeToValue(node, Attachment.class);
    }
  }
}

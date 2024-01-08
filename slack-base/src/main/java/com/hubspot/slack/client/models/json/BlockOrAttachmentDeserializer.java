package com.hubspot.slack.client.models.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hubspot.slack.client.methods.params.chat.ChatUnfurlBlocks;
import com.hubspot.slack.client.models.Attachment;
import com.hubspot.slack.client.models.ChatUnfurlBlocksOrAttachment;
import com.hubspot.slack.client.models.blocks.Block;
import java.io.IOException;

public class BlockOrAttachmentDeserializer
  extends StdDeserializer<ChatUnfurlBlocksOrAttachment> {

  private static final String BLOCKS_FIELD = "blocks";
  private static final String TYPE_FIELD = "type";

  protected BlockOrAttachmentDeserializer() {
    super(ChatUnfurlBlocksOrAttachment.class);
  }

  @Override
  public ChatUnfurlBlocksOrAttachment deserialize(
    JsonParser jsonParser,
    DeserializationContext context
  ) throws IOException {
    ObjectCodec codec = jsonParser.getCodec();
    JsonNode node = codec.readTree(jsonParser);

    if (node.has(BLOCKS_FIELD)) {
      return codec.treeToValue(node, ChatUnfurlBlocks.class);
    } else if (node.has(TYPE_FIELD)) {
      return codec.treeToValue(node, Block.class);
    } else {
      return codec.treeToValue(node, Attachment.class);
    }
  }
}

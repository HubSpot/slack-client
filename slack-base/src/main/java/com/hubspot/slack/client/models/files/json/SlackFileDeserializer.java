package com.hubspot.slack.client.models.files.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hubspot.slack.client.models.files.SlackAccessDeniedFile;
import com.hubspot.slack.client.models.files.SlackFile;
import com.hubspot.slack.client.models.files.SlackFileType;
import com.hubspot.slack.client.models.files.SlackUnknownFiletype;
import com.hubspot.slack.client.models.files.SlackUnknownFiletypeIF;
import java.io.IOException;
import java.util.Optional;

public class SlackFileDeserializer extends StdDeserializer<SlackFile> {

  private static final String FILE_ACCESS_FIELD = "file_access";
  private static final String FILE_TYPE_FIELD = "filetype";
  private static final String ACCESS_DENIED = "access_denied";

  public SlackFileDeserializer() {
    super(SlackFile.class);
  }

  @Override
  public SlackFile deserialize(JsonParser p, DeserializationContext ctxt)
    throws IOException, JsonProcessingException {
    ObjectCodec codec = p.getCodec();
    JsonNode node = codec.readTree(p);

    if (node.has(FILE_ACCESS_FIELD)) {
      String fileAccess = node.get(FILE_ACCESS_FIELD).asText();
      if (fileAccess.equalsIgnoreCase(ACCESS_DENIED)) {
        return codec.treeToValue(node, SlackAccessDeniedFile.class);
      }
    }

    Optional<SlackFileType> fileType = SlackFileType.tryParse(
      node.get(FILE_TYPE_FIELD).asText()
    );
    if (fileType.isPresent()) {
      return codec.treeToValue(node, fileType.get().getFileTypeClass());
    }

    ((ObjectNode) node).remove("filetype");
    return codec.treeToValue(node, SlackUnknownFiletype.class);
  }
}

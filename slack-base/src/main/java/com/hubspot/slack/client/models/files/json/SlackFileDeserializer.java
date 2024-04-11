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
import com.hubspot.slack.client.models.files.SlackFileDeletedFile;
import com.hubspot.slack.client.models.files.SlackFileError;
import com.hubspot.slack.client.models.files.SlackFileNotFoundFile;
import com.hubspot.slack.client.models.files.SlackFileTombstone;
import com.hubspot.slack.client.models.files.SlackFileType;
import com.hubspot.slack.client.models.files.SlackUnknownFiletype;
import com.hubspot.slack.client.models.response.SlackErrorType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SlackFileDeserializer extends StdDeserializer<SlackFile> {

  private static final String FILE_ACCESS_FIELD = "file_access";
  private static final String FILE_MODE_FIELD = "mode";
  private static final String FILE_TYPE_FIELD = "filetype";
  private static final Map<String, Class<? extends SlackFileError>> FILE_TYPE_ERROR_CLASSES;

  static {
    FILE_TYPE_ERROR_CLASSES = new HashMap<>();
    FILE_TYPE_ERROR_CLASSES.put(
      SlackErrorType.ACCESS_DENIED.getCode(),
      SlackAccessDeniedFile.class
    );
    FILE_TYPE_ERROR_CLASSES.put(
      SlackErrorType.FILE_NOT_FOUND.getCode(),
      SlackFileNotFoundFile.class
    );
    FILE_TYPE_ERROR_CLASSES.put(
      SlackErrorType.FILE_DELETED.getCode(),
      SlackFileDeletedFile.class
    );
  }

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
      Optional<? extends Class<? extends SlackFileError>> errorClass =
        Optional.ofNullable(FILE_TYPE_ERROR_CLASSES.get(fileAccess.toLowerCase()));
      if (errorClass.isPresent()) {
        return codec.treeToValue(node, errorClass.get());
      }
    }

    if (node.has(FILE_MODE_FIELD)) {
      String fileMode = node.get(FILE_MODE_FIELD).asText();
      if (fileMode.equals("tombstone")) {
        return codec.treeToValue(node, SlackFileTombstone.class);
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

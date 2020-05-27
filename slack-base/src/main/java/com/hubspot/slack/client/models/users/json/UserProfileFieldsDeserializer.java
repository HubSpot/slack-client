package com.hubspot.slack.client.models.users.json;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hubspot.slack.client.models.users.ProfileField;

public class UserProfileFieldsDeserializer extends StdDeserializer<Optional<Map<String, ProfileField>>> {
  protected UserProfileFieldsDeserializer() {
    super(Optional.class);
  }

  @Override
  public Optional<Map<String, ProfileField>> deserialize(JsonParser parser,
                                                         DeserializationContext context) throws IOException {
    ObjectCodec codec = parser.getCodec();
    TreeNode node = codec.readTree(parser);

    if (node.isArray()) {
      return Optional.empty();
    }
    Map<String, ProfileField> fieldMap = codec.treeToValue(node, Map.class);

    return fieldMap.isEmpty() ? Optional.empty() : Optional.of(fieldMap);
  }
}

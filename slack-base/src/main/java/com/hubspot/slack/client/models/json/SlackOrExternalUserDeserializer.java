package com.hubspot.slack.client.models.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hubspot.slack.client.models.calls.ExternalCallsUser;
import com.hubspot.slack.client.models.calls.SlackCallsUser;
import com.hubspot.slack.client.models.calls.SlackInternalOrExternalUser;
import java.io.IOException;

public class SlackOrExternalUserDeserializer
  extends StdDeserializer<SlackInternalOrExternalUser> {

  protected SlackOrExternalUserDeserializer() {
    super(SlackInternalOrExternalUser.class);
  }

  @Override
  public SlackInternalOrExternalUser deserialize(
    JsonParser jsonParser,
    DeserializationContext context
  ) throws IOException {
    ObjectCodec codec = jsonParser.getCodec();
    JsonNode node = codec.readTree(jsonParser);

    if (node.has("slack_id")) {
      return codec.treeToValue(node, SlackCallsUser.class);
    } else {
      return codec.treeToValue(node, ExternalCallsUser.class);
    }
  }
}

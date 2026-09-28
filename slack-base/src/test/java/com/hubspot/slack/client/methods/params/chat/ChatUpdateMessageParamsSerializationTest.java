package com.hubspot.slack.client.methods.params.chat;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import org.junit.Test;

public class ChatUpdateMessageParamsSerializationTest {

  private static final ObjectMapper OBJECT_MAPPER = ObjectMapperUtils.mapper();

  @Test
  public void itSerializesFileIdsAsJsonArray() {
    ChatUpdateMessageParams params = ChatUpdateMessageParams
      .builder()
      .setChannelId("C123")
      .setTs("1790587670.603089")
      .setText("testText")
      .addFileIds("F0C4SK2P1EZ", "F0C4SK0RGGM")
      .build();

    JsonNode json = OBJECT_MAPPER.valueToTree(params);

    assertThat(json.get("file_ids").isArray()).isTrue();
    assertThat(json.get("file_ids"))
      .extracting(JsonNode::asText)
      .containsExactly("F0C4SK2P1EZ", "F0C4SK0RGGM");
  }

  @Test
  public void itOmitsFileIdsWhenEmpty() {
    ChatUpdateMessageParams params = ChatUpdateMessageParams
      .builder()
      .setChannelId("C123")
      .setTs("1790587670.603089")
      .setText("testText")
      .build();

    JsonNode json = OBJECT_MAPPER.valueToTree(params);

    assertThat(json.has("file_ids")).isFalse();
  }
}

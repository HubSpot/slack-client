package com.hubspot.slack.client.models.conversations;

import static org.assertj.core.api.Assertions.assertThat;

import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.response.conversations.ConversationKickResponse;
import java.io.IOException;
import org.junit.Test;

public class ConversationKickResponseDeserializationTest {

  @Test
  public void itDeserializesChatScheduledMessagesListResponse() throws IOException {
    ConversationKickResponse conversationKickResponse =
      fetchAndDeserializeConversationKickResponse("conversation_kick_response.json");

    assertThat(conversationKickResponse.isOk()).isTrue();
  }

  private ConversationKickResponse fetchAndDeserializeConversationKickResponse(
    String jsonFileName
  ) throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(jsonFileName);
    return ObjectMapperUtils.mapper().readValue(rawJson, ConversationKickResponse.class);
  }
}

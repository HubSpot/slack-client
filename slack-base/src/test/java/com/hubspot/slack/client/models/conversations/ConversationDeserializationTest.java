package com.hubspot.slack.client.models.conversations;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.response.conversations.ConversationsInfoResponse;

public class ConversationDeserializationTest {

  @Test
  public void conversationSerializationIsIdempotent() throws IOException {
    Conversation conversation = getConversation();
    assertThat(ObjectMapperUtils.mapper().readValue(ObjectMapperUtils.mapper().writeValueAsString(conversation), Conversation.class)).isEqualTo(conversation);
  }

  @Test
  public void conversationSerializesWithIsGeneral() throws IOException {
    Conversation conversation = getConversation();
    assertTrue(conversation.isGeneral().isPresent() && conversation.isGeneral().get());
  }

  private Conversation getConversation() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("conversation_response_with_is_general.json");
    return ObjectMapperUtils.mapper().readValue(rawJson, ConversationsInfoResponse.class).getConversation();
  }
}

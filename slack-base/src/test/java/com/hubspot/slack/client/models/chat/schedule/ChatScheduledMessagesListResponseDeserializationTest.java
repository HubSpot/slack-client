package com.hubspot.slack.client.models.chat.schedule;

import static org.assertj.core.api.Assertions.assertThat;

import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.ScheduledMessage;
import com.hubspot.slack.client.models.response.chat.ChatScheduledMessagesListResponse;
import java.io.IOException;
import java.util.Set;
import org.junit.Test;

public class ChatScheduledMessagesListResponseDeserializationTest {

  @Test
  public void itDeserializesChatScheduledMessagesListResponse() throws IOException {
    ChatScheduledMessagesListResponse chatScheduledMessagesListResponse = fetchAndDeserializeChatScheduledMessagesListResponse(
      "chat_scheduled_message_list_response.json"
    );

    Set<ScheduledMessage> scheduledMessages = chatScheduledMessagesListResponse.getScheduledMessages();
    assertThat(scheduledMessages)
      .contains(
        ScheduledMessage
          .builder()
          .setId("Q029Z55TT7G")
          .setChannelId("C010YD9600Z")
          .setDateCreated(1628085547)
          .setPostAt(1628091337)
          .setText("Test Message")
          .build()
      );
  }

  private ChatScheduledMessagesListResponse fetchAndDeserializeChatScheduledMessagesListResponse(
    String jsonFileName
  ) throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(jsonFileName);
    return ObjectMapperUtils
      .mapper()
      .readValue(rawJson, ChatScheduledMessagesListResponse.class);
  }
}

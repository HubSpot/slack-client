package com.hubspot.slack.client.models.chat.schedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.response.chat.ChatScheduleMessageResponse;

public class ChatScheduleMessageResponseDeserializationTest {
  @Test
  public void itDeserializesChatScheduleMessageResponse() throws IOException {
    ChatScheduleMessageResponse chatScheduleMessageParams =
        fetchAndDeserializeChatScheduleMessageResponse("chat_schedule_message_response.json");

    String channel = chatScheduleMessageParams.getChannel();
    String postAt = chatScheduleMessageParams.getPostAt();
    String scheduledMessageId = chatScheduleMessageParams.getScheduledMessageId();
    Map<String, Object> message = chatScheduleMessageParams.getMessage();
    assertThat(channel).isEqualTo("C1H9RESGL");
    assertThat(postAt).isEqualTo("1562180400");
    assertThat(scheduledMessageId).isEqualTo("Q1298393284");
    assertThat(message).hasSize(6);
  }

  private ChatScheduleMessageResponse fetchAndDeserializeChatScheduleMessageResponse(String jsonFileName) throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(jsonFileName);
    return ObjectMapperUtils.mapper().readValue(rawJson, ChatScheduleMessageResponse.class);
  }
}

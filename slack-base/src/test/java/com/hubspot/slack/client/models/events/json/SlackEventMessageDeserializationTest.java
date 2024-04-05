package com.hubspot.slack.client.models.events.json;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.events.SlackEventMessage;
import com.hubspot.slack.client.models.events.SlackEventType;
import java.io.IOException;

import com.hubspot.slack.client.models.events.SlackEventWrapper;
import org.junit.Test;

public class SlackEventMessageDeserializationTest {

  @Test
  public void itDeserializes() throws IOException {
    SlackEventMessage slackEvent = fetchAndDeserializeSlackEvent(
      "message_shared_with_file.json"
    );
    assertThat(slackEvent.getType()).isEqualTo(SlackEventType.MESSAGE);
    assertThat(slackEvent.getFiles().size()).isEqualTo(2);
  }

  private SlackEventMessage fetchAndDeserializeSlackEvent(String jsonFileName)
    throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(jsonFileName);
    return deserializeSlackEvent(rawJson);
  }

  private SlackEventMessage deserializeSlackEvent(String rawJson) throws IOException {
    SlackEventWrapper<SlackEventMessage> eventWrapper = ObjectMapperUtils
      .mapper()
      .readValue(rawJson, new TypeReference<>() {});
    return eventWrapper.getEvent();
  }
}

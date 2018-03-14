package com.hubspot.slack.client.models.events.json;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.events.SlackEvent;
import com.hubspot.slack.client.models.events.SlackEventWrapper;

public class EventDeserializerTest {
  @Test
  public void itCanDeserMessageChanged1() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("message_change1.json");
    SlackEventWrapper<? extends SlackEvent> eventWrapper = ObjectMapperUtils.mapper().readValue(
        rawJson, new TypeReference<SlackEventWrapper<? extends SlackEvent>>() {
        }
    );
    SlackEvent event = eventWrapper.getEvent().toDetailedEvent();
  }

  @Test
  public void itCanDeserMessageChanged2() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("message_change2.json");
    SlackEventWrapper<? extends SlackEvent> eventWrapper = ObjectMapperUtils.mapper().readValue(
        rawJson, new TypeReference<SlackEventWrapper<? extends SlackEvent>>() {
        }
    );
    SlackEvent event = eventWrapper.getEvent().toDetailedEvent();
  }

  @Test
  public void itCanDeserMessageDeleted() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("message_delete.json");
    SlackEventWrapper<? extends SlackEvent> eventWrapper = ObjectMapperUtils.mapper().readValue(
        rawJson, new TypeReference<SlackEventWrapper<? extends SlackEvent>>() {
        }
    );
    SlackEvent event = eventWrapper.getEvent().toDetailedEvent();
  }

  @Test
  public void itCanDeserMessageFileShared() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("message_file_shared.json");
    SlackEventWrapper<? extends SlackEvent> eventWrapper = ObjectMapperUtils.mapper().readValue(
        rawJson, new TypeReference<SlackEventWrapper<? extends SlackEvent>>() {
        }
    );
    SlackEvent event = eventWrapper.getEvent().toDetailedEvent();
  }

  @Test
  public void itCanDeserMessageThreadBroadcast() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("message_thread_broadcast.json");
    SlackEventWrapper<? extends SlackEvent> eventWrapper = ObjectMapperUtils.mapper().readValue(
        rawJson, new TypeReference<SlackEventWrapper<? extends SlackEvent>>() {
        }
    );
    SlackEvent event = eventWrapper.getEvent().toDetailedEvent();
  }

  @Test
  public void itCanDeserMessageMeMessage() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("message_me_message.json");
    SlackEventWrapper<? extends SlackEvent> eventWrapper = ObjectMapperUtils.mapper().readValue(
        rawJson, new TypeReference<SlackEventWrapper<? extends SlackEvent>>() {
        }
    );
    SlackEvent event = eventWrapper.getEvent().toDetailedEvent();
  }
}

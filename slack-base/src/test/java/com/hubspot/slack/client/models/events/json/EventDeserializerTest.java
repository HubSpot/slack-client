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
    fetchAndDeserializeSlackEvent("message_change1.json");
  }

  @Test
  public void itCanDeserMessageChanged2() throws IOException {
    fetchAndDeserializeSlackEvent("message_change2.json");
  }

  @Test
  public void itCanDeserMessageDeleted() throws IOException {
    fetchAndDeserializeSlackEvent("message_delete.json");
  }

  @Test
  public void itCanDeserMessageFileShared() throws IOException {
    fetchAndDeserializeSlackEvent("message_file_shared.json");
  }

  @Test
  public void itCanDeserMessageThreadBroadcast() throws IOException {
    fetchAndDeserializeSlackEvent("message_thread_broadcast.json");
  }

  @Test
  public void itCanDeserMessageMeMessage() throws IOException {
    fetchAndDeserializeSlackEvent("message_me_message.json");
  }

  @Test
  public void itCanDeserChannelCreatedMessage() throws IOException {
    fetchAndDeserializeSlackEvent("channel_created_message.json");
  }

  @Test
  public void itCanDeserChannelDeletedMessage() throws IOException {
    fetchAndDeserializeSlackEvent("channel_deleted_message.json");
  }

  @Test
  public void itCanDeserChannelRenamedEvent() throws IOException {
    fetchAndDeserializeSlackEvent("channel_renamed_event.json");
  }

  @Test
  public void itCanDeserChannelArchivedEvent() throws IOException {
    fetchAndDeserializeSlackEvent("channel_archived_event.json");
  }

  @Test
  public void itCanDeserChannelUnarchivedEvent() throws IOException {
    fetchAndDeserializeSlackEvent("channel_unarchived_event.json");
  }

  private void fetchAndDeserializeSlackEvent(String jsonFileName) throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(jsonFileName);
    deserializeSlackEvent(rawJson);
  }

  private SlackEvent deserializeSlackEvent(String rawJson) throws IOException {
    SlackEventWrapper<? extends SlackEvent> eventWrapper = ObjectMapperUtils.mapper().readValue(
        rawJson, new TypeReference<SlackEventWrapper<? extends SlackEvent>>() {
        }
    );
    return eventWrapper.getEvent().toDetailedEvent();
  }
}

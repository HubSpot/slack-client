package com.hubspot.slack.client.models.events.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.events.SlackEvent;
import com.hubspot.slack.client.models.events.SlackEventType;
import com.hubspot.slack.client.models.events.SlackEventWrapper;
import com.hubspot.slack.client.models.events.user.SlackMemberJoinedChannelEvent;

public class EventDeserializerTest {
  @Test
  public void itCanDeserMessageChanged1() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("message_change1.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.MESSAGE);
  }

  @Test
  public void itCanDeserMessageChanged2() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("message_change2.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.MESSAGE);
  }

  @Test
  public void itCanDeserMessageDeleted() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("message_delete.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.MESSAGE);
  }

  @Test
  public void itCanDeserMessageFileShared() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("message_file_shared.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.MESSAGE);
  }

  @Test
  public void itCanDeserMessageThreadBroadcast() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("message_thread_broadcast.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.MESSAGE);
  }

  @Test
  public void itCanDeserMessageMeMessage() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("message_me_message.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.MESSAGE);
  }

  @Test
  public void itCanDeserChannelCreatedMessage() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("channel_created_message.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.CHANNEL_CREATED);
  }

  @Test
  public void itCanDeserChannelDeletedMessage() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("channel_deleted_message.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.CHANNEL_DELETED);
  }

  @Test
  public void itCanDeserChannelRenamedEvent() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("channel_renamed_event.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.CHANNEL_RENAME);
  }

  @Test
  public void itCanDeserChannelArchivedEvent() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("channel_archived_event.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.CHANNEL_ARCHIVE);
  }

  @Test
  public void itCanDeserChannelUnarchivedEvent() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("channel_unarchived_event.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.CHANNEL_UNARCHIVE);
  }

  @Test
  public void itCanDeserMemberJoinedChannelEventWithoutInviter() throws IOException {
    SlackMemberJoinedChannelEvent event = fetchAndDeserializeSlackEvent("member_joined_channel.json").toDetailedEvent();
    assertThat(event.getType()).isEqualTo(SlackEventType.MEMBER_JOINED_CHANNEL);
  }

  @Test
  public void itCanDeserMemberJoinedChannelEventWithInviter() throws IOException {
    SlackMemberJoinedChannelEvent event = fetchAndDeserializeSlackEvent("member_joined_channel_with_inviter.json").toDetailedEvent();
    assertThat(event.getType()).isEqualTo(SlackEventType.MEMBER_JOINED_CHANNEL);
  }

  private SlackEvent fetchAndDeserializeSlackEvent(String jsonFileName) throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(jsonFileName);
    return deserializeSlackEvent(rawJson);
  }

  private SlackEvent deserializeSlackEvent(String rawJson) throws IOException {
    SlackEventWrapper<? extends SlackEvent> eventWrapper = ObjectMapperUtils.mapper().readValue(
        rawJson, new TypeReference<SlackEventWrapper<? extends SlackEvent>>() {
        }
    );
    return eventWrapper.getEvent().toDetailedEvent();
  }
}

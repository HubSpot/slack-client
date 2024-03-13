package com.hubspot.slack.client.models.events.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.methods.interceptor.HasChannel;
import com.hubspot.slack.client.models.ChannelType;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.events.SlackEvent;
import com.hubspot.slack.client.models.events.SlackEventMessage;
import com.hubspot.slack.client.models.events.SlackEventType;
import com.hubspot.slack.client.models.events.SlackEventWrapper;
import com.hubspot.slack.client.models.events.links.SlackLinkSharedEvent;
import com.hubspot.slack.client.models.events.user.SlackMemberJoinedChannelEvent;
import com.hubspot.slack.client.models.events.user.SlackMemberLeftChannelEvent;
import com.hubspot.slack.client.models.events.user.SlackUserChangeEvent;
import java.io.IOException;
import org.junit.Test;

public class EventDeserializerTest {

  @Test
  public void itCanDeserAppHomeOpened() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("app_home_opened.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.APP_HOME_OPENED);
  }

  @Test
  public void itCanDeserAppMentionMessage() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("app_mention_message.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.APP_MENTION);
  }

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
  public void itCanDeserChannelSharedEvent() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("channel_shared_event.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.CHANNEL_SHARED);

    System.out.println(event);
  }

  @Test
  public void itCanDeserChannelUnarchivedEvent() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("channel_unarchived_event.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.CHANNEL_UNARCHIVE);
  }

  @Test
  public void itCanDeserializeGroupArchiveEvent() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("group_archive_event.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.GROUP_ARCHIVE);
  }

  @Test
  public void itCanDeserializeGroupDeletedEvent() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("group_deleted_event.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.GROUP_DELETED);
  }

  @Test
  public void itCanDeserializeGroupOpenEvent() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("group_open_event.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.GROUP_OPEN);
  }

  @Test
  public void itCanDeserializeGroupRenameEvent() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("group_rename_event.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.GROUP_RENAME);
  }

  @Test
  public void itCanDeserializeGroupUnArchiveEvent() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("group_unarchive_event.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.GROUP_UNARCHIVE);
  }

  @Test
  public void itCanDeserMemberJoinedChannelEventWithoutInviter() throws IOException {
    SlackMemberJoinedChannelEvent event =
      (SlackMemberJoinedChannelEvent) fetchAndDeserializeSlackEvent(
        "member_joined_channel.json"
      );
    assertThat(event.getType()).isEqualTo(SlackEventType.MEMBER_JOINED_CHANNEL);
    assertThat(
      ObjectMapperUtils
        .mapper()
        .readValue(
          ObjectMapperUtils.mapper().writeValueAsString(event),
          SlackMemberJoinedChannelEvent.class
        )
    )
      .isEqualTo(event);
  }

  @Test
  public void itCanDeserMemberJoinedChannelEventWithInviter() throws IOException {
    SlackMemberJoinedChannelEvent event =
      (SlackMemberJoinedChannelEvent) fetchAndDeserializeSlackEvent(
        "member_joined_channel_with_inviter.json"
      );
    assertThat(event.getType()).isEqualTo(SlackEventType.MEMBER_JOINED_CHANNEL);
    assertTrue(event.getInviterId().isPresent());
    assertThat(
      ObjectMapperUtils
        .mapper()
        .readValue(
          ObjectMapperUtils.mapper().writeValueAsString(event),
          SlackMemberJoinedChannelEvent.class
        )
    )
      .isEqualTo(event);
  }

  @Test
  public void itCanDeserMemberJoinedPrivateChannels() throws IOException {
    SlackMemberJoinedChannelEvent event =
      (SlackMemberJoinedChannelEvent) fetchAndDeserializeSlackEvent(
        "member_joined_private_channel.json"
      );
    assertThat(event.getType()).isEqualTo(SlackEventType.MEMBER_JOINED_CHANNEL);
    assertThat(event.getChannelType()).isEqualTo(ChannelType.GROUP);
    assertThat(
      ObjectMapperUtils
        .mapper()
        .readValue(
          ObjectMapperUtils.mapper().writeValueAsString(event),
          SlackMemberJoinedChannelEvent.class
        )
    )
      .isEqualTo(event);
  }

  @Test
  public void itCanHaveAChannelWhenJoining() throws IOException {
    SlackMemberJoinedChannelEvent event =
      (SlackMemberJoinedChannelEvent) fetchAndDeserializeSlackEvent(
        "member_joined_private_channel.json"
      );
    assertThat(event).isInstanceOf(HasChannel.class);
  }

  @Test
  public void itCanDeserMemberLeftPublicChannelEvent() throws IOException {
    SlackMemberLeftChannelEvent event =
      (SlackMemberLeftChannelEvent) fetchAndDeserializeSlackEvent(
        "member_left_public_channel.json"
      );
    assertThat(event.getType()).isEqualTo(SlackEventType.MEMBER_LEFT_CHANNEL);
    assertThat(
      ObjectMapperUtils
        .mapper()
        .readValue(
          ObjectMapperUtils.mapper().writeValueAsString(event),
          SlackMemberLeftChannelEvent.class
        )
    )
      .isEqualTo(event);
  }

  @Test
  public void itCanDeserMemberLeftPrivateChannelEvent() throws IOException {
    SlackMemberLeftChannelEvent event =
      (SlackMemberLeftChannelEvent) fetchAndDeserializeSlackEvent(
        "member_left_private_channel.json"
      );
    assertThat(event.getType()).isEqualTo(SlackEventType.MEMBER_LEFT_CHANNEL);
    assertThat(
      ObjectMapperUtils
        .mapper()
        .readValue(
          ObjectMapperUtils.mapper().writeValueAsString(event),
          SlackMemberLeftChannelEvent.class
        )
    )
      .isEqualTo(event);
  }

  @Test
  public void itCanHaveAChannelWhenLeaving() throws IOException {
    SlackMemberLeftChannelEvent event =
      (SlackMemberLeftChannelEvent) fetchAndDeserializeSlackEvent(
        "member_left_private_channel.json"
      );
    assertThat(event).isInstanceOf(HasChannel.class);
  }

  @Test
  public void itCanDeserUserChangeEvent() throws IOException {
    SlackUserChangeEvent event = (SlackUserChangeEvent) fetchAndDeserializeSlackEvent(
      "user_change.json"
    );
    assertThat(event.getType()).isEqualTo(SlackEventType.USER_CHANGE);
    assertThat(
      event.getUser().isDeleted().isPresent() && event.getUser().isDeleted().get()
    );
  }

  @Test
  public void itCanDeserLinkSharedEvent() throws IOException {
    SlackLinkSharedEvent event = (SlackLinkSharedEvent) fetchAndDeserializeSlackEvent(
      "link_shared.json"
    );
    assertThat(event.getType()).isEqualTo(SlackEventType.LINK_SHARED);
    assertThat(
      ObjectMapperUtils
        .mapper()
        .readValue(
          ObjectMapperUtils.mapper().writeValueAsString(event),
          SlackLinkSharedEvent.class
        )
    )
      .isEqualTo(event);
  }

  @Test
  public void itCanDeserBotMessageEventCallbacks() throws IOException {
    SlackEvent event = fetchAndDeserializeSlackEvent("bot_message_event_callback.json");
    assertThat(event.getType()).isEqualTo(SlackEventType.MESSAGE);
    assertThat(event.getClass())
      .describedAs("It should deserialize the new format into a message")
      .isEqualTo(SlackEventMessage.class);
    SlackEventMessage typedMessage = (SlackEventMessage) event;
    assertThat(typedMessage.getAttachments())
      .describedAs("It should deser the attachments")
      .hasSize(1);
    assertThat(typedMessage.getBotId())
      .describedAs("It should deser the bot id")
      .contains("B00000000");
  }

  @Test
  public void itCanDeserLinkSharedEventWithThreadTs() throws IOException {
    SlackLinkSharedEvent event = (SlackLinkSharedEvent) fetchAndDeserializeSlackEvent(
      "link_shared_with_thread_ts.json"
    );
    assertThat(event.getType()).isEqualTo(SlackEventType.LINK_SHARED);
    assertTrue(event.getThreadTs().isPresent());
    assertThat(
      ObjectMapperUtils
        .mapper()
        .readValue(
          ObjectMapperUtils.mapper().writeValueAsString(event),
          SlackLinkSharedEvent.class
        )
    )
      .isEqualTo(event);
  }

  private SlackEvent fetchAndDeserializeSlackEvent(String jsonFileName)
    throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(jsonFileName);
    return deserializeSlackEvent(rawJson);
  }

  private SlackEvent deserializeSlackEvent(String rawJson) throws IOException {
    SlackEventWrapper<? extends SlackEvent> eventWrapper = ObjectMapperUtils
      .mapper()
      .readValue(
        rawJson,
        new TypeReference<SlackEventWrapper<? extends SlackEvent>>() {}
      );
    return eventWrapper.getEvent();
  }
}

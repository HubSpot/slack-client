package com.hubspot.slack.client.methods.params.chat;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import java.io.IOException;
import org.junit.Test;

public class ChatPostMessageParamsSerializationTest {

  private static final String CHANNEL_ID = "C1234567890";
  private static final String THREAD_TS = "1791374668.950609";
  private static final String USERNAME = "hubspot (breeze) qa";

  @Test
  public void itOmitsUnsetOptionalFields() throws IOException {
    ChatPostMessageParams params = ChatPostMessageParams
      .builder()
      .setChannelId(CHANNEL_ID)
      .setText("post message test")
      .setThreadTs(THREAD_TS)
      .build();

    JsonNode json = serialize(params);

    assertThat(json.has("username")).isFalse();
    assertThat(json.has("icon_emoji")).isFalse();
    assertThat(json.has("icon_url")).isFalse();
    assertThat(json.has("link_names")).isFalse();
    assertThat(json.has("unfurl_links")).isFalse();
    assertThat(json.has("unfurl_media")).isFalse();
    assertThat(json.has("reply_broadcast")).isFalse();
    assertThat(json.has("as_user")).isFalse();
  }

  @Test
  public void itDoesNotSerializeAnyNullValues() throws IOException {
    ChatPostMessageParams params = ChatPostMessageParams
      .builder()
      .setChannelId(CHANNEL_ID)
      .setText("post message test")
      .build();

    assertThat(serialize(params).toString()).doesNotContain(":null");
  }

  @Test
  public void itSerializesSetOptionalFields() throws IOException {
    ChatPostMessageParams params = ChatPostMessageParams
      .builder()
      .setChannelId(CHANNEL_ID)
      .setText("post message test")
      .setThreadTs(THREAD_TS)
      .setUsername(USERNAME)
      .setIconEmoji(":robot_face:")
      .setIconUrl("https://example.com/icon.png")
      .setLinkNames(true)
      .setUnfurlLinks(false)
      .setUnfurlMedia(false)
      .setReplyBroadcast(true)
      .build();

    JsonNode json = serialize(params);

    assertThat(json.get("channel").asText()).isEqualTo(CHANNEL_ID);
    assertThat(json.get("thread_ts").asText()).isEqualTo(THREAD_TS);
    assertThat(json.get("username").asText()).isEqualTo(USERNAME);
    assertThat(json.get("icon_emoji").asText()).isEqualTo(":robot_face:");
    assertThat(json.get("icon_url").asText()).isEqualTo("https://example.com/icon.png");
    assertThat(json.get("link_names").asBoolean()).isTrue();
    assertThat(json.get("unfurl_links").asBoolean()).isFalse();
    assertThat(json.get("unfurl_media").asBoolean()).isFalse();
    assertThat(json.get("reply_broadcast").asBoolean()).isTrue();
  }

  @Test
  public void itKeepsEmptyCollectionsForRequiredFields() throws IOException {
    ChatPostMessageParams params = ChatPostMessageParams
      .builder()
      .setChannelId(CHANNEL_ID)
      .setText("post message test")
      .build();

    JsonNode json = serialize(params);

    assertThat(json.get("blocks").isArray()).isTrue();
    assertThat(json.get("attachments").isArray()).isTrue();
  }

  @Test
  public void itOmitsUnsetOptionalFieldsForSlashCommandResponses() throws IOException {
    SlashCommandResponseParams params = SlashCommandResponseParams
      .builder()
      .setChannelId(CHANNEL_ID)
      .setText("slash command response")
      .build();

    JsonNode json = serialize(params);

    assertThat(json.has("username")).isFalse();
    assertThat(json.toString()).doesNotContain(":null");
    assertThat(json.has("response_type")).isTrue();
  }

  private static JsonNode serialize(Object params) throws IOException {
    return ObjectMapperUtils
      .mapper()
      .readTree(ObjectMapperUtils.mapper().writeValueAsString(params));
  }
}

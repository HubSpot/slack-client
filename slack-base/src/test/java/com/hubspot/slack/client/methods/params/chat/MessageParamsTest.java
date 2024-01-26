package com.hubspot.slack.client.methods.params.chat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.Test;

import com.hubspot.slack.client.models.Attachment;

public class MessageParamsTest {

  @Test
  public void itBuildsWithAttachmentsAndNoText() {
    ChatPostEphemeralMessageParams.builder()
        .setToken("testToken")
        .setChannelId("testChannelId")
        .setUserToSendTo("testUserId")
        .addAttachments(Attachment.builder().build())
        .build();

    ChatPostMessageParams.builder()
        .setToken("testToken")
        .setChannelId("testChannelId")
        .addAttachments(Attachment.builder().build())
        .build();

    ChatUpdateMessageParams.builder()
        .setToken("testToken")
        .setTs("testTs")
        .setChannelId("testChannelId")
        .addAttachments(Attachment.builder().build())
        .build();
  }

  @Test
  public void itBuildsWithTextAndNoAttachments() {
    ChatPostEphemeralMessageParams.builder()
        .setToken("testToken")
        .setChannelId("testChannelId")
        .setUserToSendTo("testUserId")
        .setText("testText")
        .build();

    ChatPostMessageParams.builder()
        .setToken("testToken")
        .setChannelId("testChannelId")
        .setText("testText")
        .build();

    ChatUpdateMessageParams.builder()
        .setToken("testToken")
        .setTs("testTs")
        .setChannelId("testChannelId")
        .setText("testText")
        .build();
  }

  @Test
  public void itFailsToBuildWithoutTextOrAttachments() {
    boolean success = true;

    try {
      ChatPostEphemeralMessageParams.builder()
          .setToken("testToken")
          .setChannelId("testChannelId")
          .setUserToSendTo("testUserId")
          .build();
      success = false;
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage()).contains("Must include text if not providing attachments");
    }

    try {
      ChatPostMessageParams.builder()
          .setToken("testToken")
          .setChannelId("testChannelId")
          .build();
      success = false;
    } catch (IllegalStateException ise2) {
      assertThat(ise2.getMessage()).contains("Must include text if not providing attachments");
    }

    try {
      ChatUpdateMessageParams.builder()
          .setToken("testToken")
          .setTs("testTs")
          .setChannelId("testChannelId")
          .build();
      success = false;
    } catch (IllegalStateException ise3) {
      assertThat(ise3.getMessage()).contains("Must include text if not providing attachments");
    }

    if (!success) {
      fail("Should have thrown an error for MessageParam built without text or attachments!");
    }
  }
}

package com.hubspot.slack.client.methods.params.chat;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import com.hubspot.slack.client.models.Attachment;

public class ChatUnfurlParamsTest {

  @Test
  public void itCanBuildChatUnfurlParams() throws URISyntaxException {
    Attachment unfurlAttachment = Attachment.builder()
        .setText("UNFURLING TEXT!!!!!")
        .build();

    ChatUnfurlParams.builder()
        .setChannelId("C1234567890")
        .setTs("123456789.9875")
        .putUnfurls("https://example.com/12345", unfurlAttachment)
        .setUserAuthRequired(true)
        .setUserAuthMessage("User auth is required")
        .setUserAuthUrl(new URI("http://www.auth.com/"))
        .build();
  }
}

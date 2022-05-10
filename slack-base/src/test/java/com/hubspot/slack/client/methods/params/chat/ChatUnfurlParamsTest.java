package com.hubspot.slack.client.methods.params.chat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.Collections;
import org.junit.Test;

import com.hubspot.slack.client.SerializationTestBase;
import com.hubspot.slack.client.models.Attachment;
import com.hubspot.slack.client.models.blocks.Section;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;

public class ChatUnfurlParamsTest extends SerializationTestBase {

  @Test
  public void itCanBuildChatUnfurlParamsWithAttachment() throws URISyntaxException {
    Attachment attachment = Attachment.builder()
        .setText("text")
        .setCallbackId("callbackId")
        .setColor("blue")
        .setFallback("fallback")
        .setTitle("title")
        .setAuthorName("authorName")
        .setPretext("pretext")
        .build();

    ChatUnfurlParams.builder()
        .setChannelId("C1234567890")
        .setTs("123456789.9875")
        .putUnfurls("https://example.com/12345", Collections.singletonList(attachment))
        .setUserAuthRequired(true)
        .setUserAuthMessage("User auth is required")
        .setUserAuthUrl(new URI("http://www.auth.com/"))
        .build();
  }

  @Test
  public void itCanBuildChatUnfurlParamsWithBlock() throws URISyntaxException {
    Section sectionBlock = Section.builder()
        .setBlockId("blockId")
        .setText(Text.of(TextType.MARKDOWN, "This is some text"))
        .build();

    ChatUnfurlParams.builder()
        .setChannelId("C1234567890")
        .setTs("123456789.9875")
        .putUnfurls("https://example.com/12345", Collections.singletonList(sectionBlock))
        .setUserAuthRequired(true)
        .setUserAuthMessage("User auth is required")
        .setUserAuthUrl(new URI("http://www.auth.com/"))
        .build();
  }

  @Test
  public void itCanDeserChatUnfurlParamsWithAttachment() throws IOException {
    testSerialization("chat_unfurl_params_using_attachment.json", ChatUnfurlParams.class);
  }

  @Test
  public void itCanDeserChatUnfurlParamsWithBlock() throws IOException {
    testSerialization("chat_unfurl_params_using_block.json", ChatUnfurlParams.class);
  }
}

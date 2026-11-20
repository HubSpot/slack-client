package com.hubspot.slack.client.models.attachments;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.hubspot.slack.client.SerializationTestBase;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.Attachment;
import com.hubspot.slack.client.models.JsonLoader;
import java.io.IOException;
import org.junit.Test;

public class AttachmentSerializationTest extends SerializationTestBase {

  @Test
  public void testAttachmentsWithNullsSerialization() throws IOException {
    testSerialization("attachment_without_blocks_with_nulls.json", Attachment.class);
  }

  @Test
  public void testAttachmentsSerialization() throws IOException {
    testSerialization("attachment_without_blocks.json", Attachment.class);
  }

  @Test
  public void testAttachmentsWithBlocksSerialization() throws IOException {
    testSerialization("attachment_with_blocks.json", Attachment.class);
  }

  @Test
  public void testAttachmentsWithBlocksAndColorSerialization() throws IOException {
    testSerialization("attachment_with_blocks_and_color.json", Attachment.class);
  }

  @Test
  public void testAttachmentsWithBlocksColorAndFallbackSerialization()
    throws IOException {
    testSerialization("attachment_with_blocks_color_and_fallback.json", Attachment.class);
  }

  @Test
  public void itOnlyWritesBlocksColorAndFallbackForBlocksAttachment() throws IOException {
    Attachment attachment = ObjectMapperUtils
      .mapper()
      .readValue(
        JsonLoader.loadJsonFromFile("attachment_with_blocks_color_and_fallback.json"),
        Attachment.class
      );

    JsonNode json = ObjectMapperUtils.mapper().valueToTree(attachment);

    assertThat(json.fieldNames())
      .toIterable()
      .containsExactlyInAnyOrder("blocks", "color", "fallback");
  }

  @Test
  public void itOmitsAbsentColorAndFallbackForBlocksAttachment() throws IOException {
    Attachment attachment = ObjectMapperUtils
      .mapper()
      .readValue(
        JsonLoader.loadJsonFromFile("attachment_with_blocks.json"),
        Attachment.class
      );

    JsonNode json = ObjectMapperUtils.mapper().valueToTree(attachment);

    assertThat(json.fieldNames()).toIterable().containsExactly("blocks");
  }
}

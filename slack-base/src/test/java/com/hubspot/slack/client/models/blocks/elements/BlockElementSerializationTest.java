package com.hubspot.slack.client.models.blocks.elements;

import static org.assertj.core.api.Assertions.assertThat;

import com.hubspot.slack.client.SerializationTestBase;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.blocks.Block;
import com.hubspot.slack.client.models.blocks.Section;
import java.io.IOException;
import org.junit.Test;

public class BlockElementSerializationTest extends SerializationTestBase {

  @Test
  public void testBlockSerialization() throws IOException {
    testSerialization("block_elements.json", BlockElement[].class);
  }

  @Test
  public void testAttachmentMentionRichTextSerialization() throws IOException {
    testSerialization("attachment_mention_rich_text.json", Block[].class);
  }

  @Test
  public void testTeamRichTextSerialization() throws IOException {
    testSerialization("team_rich_text.json", Block[].class);
  }

  @Test
  public void testMessageMentionRichTextSerialization() throws IOException {
    testSerialization("message_mention_rich_text.json", Block[].class);
  }

  @Test
  public void testFileRichTextSerialization() throws IOException {
    testSerialization("file_rich_text.json", Block[].class);
  }

  @Test
  public void testRichTextElementsBatchSerialization() throws IOException {
    testSerialization("rich_text_elements_batch.json", Block[].class);
  }

  @Test
  public void testInteractiveElementsBatchSerialization() throws IOException {
    testSerialization("interactive_elements_batch.json", BlockElement[].class);
  }

  @Test
  public void testCitationRichTextSerialization() throws IOException {
    testSerialization("citation_rich_text.json", Block[].class);
  }

  // Null-omission guard for all 14 Messages-surface elements (Slack rejects null-valued fields).
  // Each fixture entry is required-fields-only, so every optional is ABSENT in the raw JSON. The
  // round-trip asserts readTree(raw).equals(readTree(reserialized)); since a JSON null is a present
  // NullNode (not the same as an absent key), if serialization emitted "field": null for any absent
  // optional the generated tree would gain a key the raw tree lacks and this assertion would fail.
  // The mapper sets no global serialization inclusion, so this genuinely exercises each model's
  // @JsonInclude(NON_EMPTY).
  @Test
  public void testMinimalElementsOmitNulls() throws IOException {
    testSerialization("minimal_elements.json", Block[].class);
  }

  @Test
  public void testInteractiveElementsDeserializeToExpectedTypes() throws IOException {
    BlockElement[] elements = ObjectMapperUtils
      .mapper()
      .readValue(
        JsonLoader.loadJsonFromFile("interactive_elements_batch.json"),
        BlockElement[].class
      );
    assertThat(elements[0]).isInstanceOf(WorkflowButton.class);
  }

  @Test
  public void testUnknownBlockSerialization() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("unknown_block_element.json");
    Section section = ObjectMapperUtils.mapper().readValue(rawJson, Section.class);
    assertThat(section.getAccessory().get()).isInstanceOf(UnknownBlockElement.class);
  }
}

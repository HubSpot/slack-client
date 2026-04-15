package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import java.io.IOException;
import java.util.List;
import org.junit.Test;

public class MarkdownBlockTest {

  @Test
  public void itDeserializesFromJson() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("markdown_blocks.json");
    Block[] blocks = ObjectMapperUtils.mapper().readValue(rawJson, Block[].class);
    assertThat(blocks).hasSize(2);
    assertThat(blocks[0])
      .isInstanceOf(MarkdownBlock.class)
      .extracting(block -> ((MarkdownBlock) block).getText())
      .isEqualTo(
        "Here is a JavaScript function:\n\n```javascript\nfunction greet(name) {\n  return \"Hello, \" + name + \"!\";\n}\n\nconsole.log(greet(\"world\"));\n```"
      );
    assertThat(blocks[1])
      .isInstanceOf(MarkdownBlock.class)
      .extracting(block -> ((MarkdownBlock) block).getText())
      .isEqualTo(
        "## Sprint Status\n" +
        "\n" +
        "| Task | Owner | Status |\n" +
        "|---|---|---|\n" +
        "| **Authentication** | Alice | ~~Done~~ _Shipped_ |\n" +
        "| `Dashboard` | Bob | **In Progress** |\n" +
        "| [API Docs](https://api.slack.com) | Carol | _Not Started_ |"
      );
  }

  @Test
  public void itSerializesAndDeserializes() throws IOException {
    MarkdownBlock original = MarkdownBlock.of("Hello **world**");
    ObjectMapper mapper = ObjectMapperUtils.mapper();
    String serialized = mapper.writeValueAsString(original);
    MarkdownBlock deserialized = mapper.readValue(serialized, MarkdownBlock.class);
    assertThat(deserialized.getText()).isEqualTo(original.getText());
  }

  @Test
  public void itCreatesSingleBlockForShortText() {
    List<Block> blocks = MarkdownBlock.ofLongText("Hello world");

    assertThat(blocks).hasSize(1);
    assertThat(getTextFromBlock(blocks.get(0))).isEqualTo("Hello world");
  }

  @Test
  public void itCreatesSingleBlockForTextAtExactLimit() {
    String text = "a".repeat(MarkdownBlock.MAX_CHAR_LENGTH);

    List<Block> blocks = MarkdownBlock.ofLongText(text);

    assertThat(blocks).hasSize(1);
    assertThat(getTextFromBlock(blocks.get(0))).isEqualTo(text);
  }

  @Test
  public void itSplitsTextIntoMultipleBlocksAtNewlineBoundaries() {
    String line = "a".repeat(MarkdownBlock.MAX_CHAR_LENGTH - 100);
    String text = line + "\n" + line;

    List<Block> blocks = MarkdownBlock.ofLongText(text);

    assertThat(blocks).hasSize(2);
    assertThat(getTextFromBlock(blocks.get(0))).isEqualTo(line);
    assertThat(getTextFromBlock(blocks.get(1))).isEqualTo(line);
  }

  @Test
  public void itHandlesSingleLineLongerThanMaxCharLength() {
    String longLine = "a".repeat(MarkdownBlock.MAX_CHAR_LENGTH + 500);

    List<Block> blocks = MarkdownBlock.ofLongText(longLine);

    assertThat(blocks).hasSize(1);
    assertThat(getTextFromBlock(blocks.get(0)).length())
      .isLessThanOrEqualTo(MarkdownBlock.MAX_CHAR_LENGTH);
  }

  @Test
  public void itPreservesTrailingBlockContent() {
    String firstLine = "a".repeat(MarkdownBlock.MAX_CHAR_LENGTH - 10);
    String trailingLine = "trailing content";
    String text = firstLine + "\n" + trailingLine;

    List<Block> blocks = MarkdownBlock.ofLongText(text);

    assertThat(blocks).hasSize(2);
    assertThat(getTextFromBlock(blocks.get(1))).isEqualTo(trailingLine);
  }

  @Test
  public void itTruncatesWithSuffixWhenSingleBlockExceedsLimit() {
    String longText = "a".repeat(MarkdownBlock.MAX_CHAR_LENGTH + 100);

    MarkdownBlock block = MarkdownBlock.of(longText);

    assertThat(block.getText().length())
      .isLessThanOrEqualTo(MarkdownBlock.MAX_CHAR_LENGTH);
    assertThat(block.getText()).endsWith("_Response truncated due to length._");
  }

  @Test
  public void itDoesNotTruncateTextWithinLimit() {
    MarkdownBlock block = MarkdownBlock.of("Hello world");

    assertThat(block.getText()).isEqualTo("Hello world");
  }

  @Test
  public void itReturnsCorrectType() {
    MarkdownBlock block = MarkdownBlock.of("test");

    assertThat(block.getType()).isEqualTo("markdown");
  }

  @Test
  public void itReturnsEmptyBlockId() {
    MarkdownBlock block = MarkdownBlock.of("test");

    assertThat(block.getBlockId()).isEmpty();
  }

  private String getTextFromBlock(Block block) {
    return ObjectMapperUtils.mapper().valueToTree(block).get("text").asText();
  }
}

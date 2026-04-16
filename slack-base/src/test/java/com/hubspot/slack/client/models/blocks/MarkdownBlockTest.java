package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import java.io.IOException;
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
}

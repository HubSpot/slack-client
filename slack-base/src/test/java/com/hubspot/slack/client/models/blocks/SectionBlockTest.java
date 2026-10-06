package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;
import java.io.IOException;
import org.junit.Test;

public class SectionBlockTest {

  private static final ObjectMapper MAPPER = ObjectMapperUtils.mapper();
  private static final String SECTION_TEXT = "A long section that should never collapse";

  @Test
  public void itSerializesExpandWhenSet() {
    Section section = Section
      .builder()
      .setText(Text.of(TextType.MARKDOWN, SECTION_TEXT))
      .setExpand(true)
      .build();

    JsonNode json = MAPPER.valueToTree(section);

    assertThat(json.get("expand").asBoolean()).isTrue();
  }

  @Test
  public void itSerializesExpandFalseWhenSet() {
    Section section = Section
      .builder()
      .setText(Text.of(TextType.MARKDOWN, SECTION_TEXT))
      .setExpand(false)
      .build();

    JsonNode json = MAPPER.valueToTree(section);

    assertThat(json.has("expand")).isTrue();
    assertThat(json.get("expand").isBoolean()).isTrue();
    assertThat(json.get("expand").booleanValue()).isFalse();
  }

  @Test
  public void itOmitsExpandWhenNotSet() {
    Section section = Section.of(Text.of(TextType.MARKDOWN, SECTION_TEXT));

    JsonNode json = MAPPER.valueToTree(section);

    assertThat(json.has("expand")).isFalse();
  }

  @Test
  public void itDeserializesExpand() throws IOException {
    String rawJson =
      "{\"type\":\"section\",\"text\":{\"type\":\"mrkdwn\",\"text\":\"" +
      SECTION_TEXT +
      "\"},\"expand\":true}";

    Block block = MAPPER.readValue(rawJson, Block.class);

    assertThat(block)
      .isInstanceOfSatisfying(
        Section.class,
        section -> assertThat(section.getExpand()).hasValue(true)
      );
  }
}

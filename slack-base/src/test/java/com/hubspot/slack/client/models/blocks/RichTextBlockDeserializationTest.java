package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextBroadcastElement;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextBroadcastRange;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextChannelElement;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextColorElement;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextDateElement;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextEmojiElement;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextLinkElement;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextSection;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextTextElement;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextUserElement;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextUserGroupElement;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class RichTextBlockDeserializationTest {

  private static final ObjectMapper MAPPER = ObjectMapperUtils.mapper();
  private static final int SECTION_WITH_BROADCAST_ELEMENT_INDEX = 0;
  private static final int SECTION_WITH_COLOR_ELEMENT_INDEX = 1;
  private static final int SECTION_WITH_CHANNEL_ELEMENT_INDEX = 2;
  private static final int SECTION_WITH_DATE_ELEMENT_INDEX = 3;
  private static final int SECTION_WITH_EMOJI_ELEMENTS_INDEX = 4;
  private static final int SECTION_WITH_LINK_ELEMENT_INDEX = 5;
  private static final int SECTION_WITH_TEXT_ELEMENTS_INDEX = 6;
  private static final int SECTION_WITH_USER_ELEMENT_INDEX = 7;
  private static final int SECTION_WITH_USERGROUP_ELEMENT_INDEX = 8;
  private static final int SECTION_WITH_MULTIPLE_ELEMENTS_INDEX = 9;
  private static RichTextSection[] sections;

  @BeforeClass
  public static void loadSections() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("rich_text_blocks.json");
    JsonNode root = MAPPER.readTree(rawJson);
    sections = new RichTextSection[root.size()];
    for (int i = 0; i < root.size(); i++) {
      JsonNode sectionNode = root.get(i).get("elements").get(0);
      sections[i] = MAPPER.treeToValue(sectionNode, RichTextSection.class);
    }
  }

  @Test
  public void itDeserializesBroadcastElement() {
    assertThat(sections[SECTION_WITH_BROADCAST_ELEMENT_INDEX].getElements())
      .containsExactly(
        RichTextBroadcastElement
          .builder()
          .setRange(RichTextBroadcastRange.EVERYONE)
          .build()
      );
  }

  @Test
  public void itDeserializesColorElement() {
    assertThat(sections[SECTION_WITH_COLOR_ELEMENT_INDEX].getElements())
      .containsExactly(RichTextColorElement.builder().setValue("#F405B3").build());
  }

  @Test
  public void itDeserializesChannelElement() {
    assertThat(sections[SECTION_WITH_CHANNEL_ELEMENT_INDEX].getElements())
      .containsExactly(
        RichTextChannelElement.builder().setChannelId("C123ABC456").build()
      );
  }

  @Test
  public void itDeserializesDateElement() {
    assertThat(sections[SECTION_WITH_DATE_ELEMENT_INDEX].getElements())
      .containsExactly(
        RichTextDateElement
          .builder()
          .setTimestamp(1720710212L)
          .setFormat("{date_num} at {time}")
          .setFallback("timey")
          .build()
      );
  }

  @Test
  public void itDeserializesEmojiElements() {
    assertThat(sections[SECTION_WITH_EMOJI_ELEMENTS_INDEX].getElements())
      .containsExactly(
        RichTextEmojiElement.builder().setName("basketball").build(),
        RichTextTextElement.builder().setText(" ").build(),
        RichTextEmojiElement.builder().setName("snowboarder").build(),
        RichTextTextElement.builder().setText(" ").build(),
        RichTextEmojiElement.builder().setName("checkered_flag").build()
      );
  }

  @Test
  public void itDeserializesLinkElement() {
    assertThat(sections[SECTION_WITH_LINK_ELEMENT_INDEX].getElements())
      .containsExactly(
        RichTextLinkElement.builder().setUrl("https://docs.slack.dev").build()
      );
  }

  @Test
  public void itDeserializesTextElementsWithStyle() {
    assertThat(sections[SECTION_WITH_TEXT_ELEMENTS_INDEX].getElements())
      .containsExactly(
        RichTextTextElement.builder().setText("Hello there, ").build(),
        RichTextTextElement
          .builder()
          .setText("I am a bold rich text block!")
          .setStyle(RichTextTextElement.Style.builder().setBold(true).build())
          .build()
      );
  }

  @Test
  public void itDeserializesUserElement() {
    assertThat(sections[SECTION_WITH_USER_ELEMENT_INDEX].getElements())
      .containsExactly(RichTextUserElement.builder().setUserId("U123ABC456").build());
  }

  @Test
  public void itDeserializesUsergroupElement() {
    assertThat(sections[SECTION_WITH_USERGROUP_ELEMENT_INDEX].getElements())
      .containsExactly(
        RichTextUserGroupElement.builder().setUserGroupId("G123ABC456").build()
      );
  }

  @Test
  public void itDeserializesMultipleElementsInSection() {
    assertThat(sections[SECTION_WITH_MULTIPLE_ELEMENTS_INDEX].getElements())
      .containsExactly(
        RichTextUserElement
          .builder()
          .setUserId("U123ABC456")
          .setStyle(RichTextUserElement.Style.builder().setUnderline(true).build())
          .build(),
        RichTextUserGroupElement.builder().setUserGroupId("G123ABC456").build(),
        RichTextDateElement
          .builder()
          .setTimestamp(1720710212L)
          .setFormat("{date_num} at {time}")
          .setFallback("timey")
          .setStyle(RichTextDateElement.Style.builder().setClientHighlight(true).build())
          .build()
      );
  }
}

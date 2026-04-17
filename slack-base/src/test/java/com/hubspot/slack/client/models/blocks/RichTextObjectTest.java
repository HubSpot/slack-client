package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextList;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextListStyle;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextObject;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextPreformatted;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextQuote;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextSection;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextTextElement;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class RichTextObjectTest {

  private static final ObjectMapper MAPPER = ObjectMapperUtils.mapper();
  private static final int PREFORMATTED_INDEX = 0;
  private static final int LIST_INDEX = 1;
  private static final int LIST_REQUIRED_ONLY_INDEX = 2;
  private static final int LIST_ALL_OPTIONAL_INDEX = 3;
  private static final int QUOTE_INDEX = 4;
  private static RichTextObject[] objects;

  @BeforeClass
  public static void loadObjects() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("rich_text_objects.json");
    JsonNode root = MAPPER.readTree(rawJson);
    objects = new RichTextObject[root.size()];
    for (int i = 0; i < root.size(); i++) {
      JsonNode objectNode = root.get(i).get("elements").get(0);
      objects[i] = MAPPER.treeToValue(objectNode, RichTextObject.class);
    }
  }

  @Test
  public void itDeserializesPreformattedBlock() {
    assertThat(objects[PREFORMATTED_INDEX])
      .isEqualTo(
        RichTextPreformatted
          .builder()
          .addElements(RichTextTextElement.builder().setText("const x = 1;").build())
          .setBorder(0)
          .setLanguage("javascript")
          .build()
      );
  }

  @Test
  public void itDeserializesListBlock() {
    assertThat(objects[LIST_INDEX])
      .isEqualTo(
        RichTextList
          .builder()
          .setStyle(RichTextListStyle.BULLET)
          .setBorder(1)
          .addElements(
            RichTextSection
              .builder()
              .addElements(RichTextTextElement.builder().setText("Huddles").build())
              .build(),
            RichTextSection
              .builder()
              .addElements(RichTextTextElement.builder().setText("Canvas").build())
              .build(),
            RichTextSection
              .builder()
              .addElements(
                RichTextTextElement.builder().setText("Developing with Block Kit").build()
              )
              .build()
          )
          .build()
      );
  }

  @Test
  public void itDeserializesListBlockWithOnlyRequiredFields() {
    assertThat(objects[LIST_REQUIRED_ONLY_INDEX])
      .isEqualTo(
        RichTextList
          .builder()
          .setStyle(RichTextListStyle.ORDERED)
          .addElements(
            RichTextSection
              .builder()
              .addElements(RichTextTextElement.builder().setText("Item").build())
              .build()
          )
          .build()
      );
  }

  @Test
  public void itDeserializesListBlockWithAllOptionalFields() {
    assertThat(objects[LIST_ALL_OPTIONAL_INDEX])
      .isEqualTo(
        RichTextList
          .builder()
          .setStyle(RichTextListStyle.BULLET)
          .setBorder(2)
          .setIndent(3)
          .setOffset(1)
          .addElements(
            RichTextSection
              .builder()
              .addElements(RichTextTextElement.builder().setText("Nested item").build())
              .build()
          )
          .build()
      );
  }

  @Test
  public void itDeserializesQuoteBlock() {
    assertThat(objects[QUOTE_INDEX])
      .isEqualTo(
        RichTextQuote
          .builder()
          .addElements(
            RichTextTextElement
              .builder()
              .setText("What we need is good examples in our documentation.")
              .build()
          )
          .setBorder(0)
          .build()
      );
  }
}

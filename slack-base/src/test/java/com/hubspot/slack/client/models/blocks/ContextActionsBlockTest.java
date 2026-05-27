package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.blocks.elements.contextactionselements.ContextActionsElement;
import com.hubspot.slack.client.models.blocks.elements.contextactionselements.FeedbackButton;
import com.hubspot.slack.client.models.blocks.elements.contextactionselements.FeedbackButtonsElement;
import com.hubspot.slack.client.models.blocks.elements.contextactionselements.IconButtonElement;
import com.hubspot.slack.client.models.blocks.elements.contextactionselements.UnknownContextActionsElement;
import com.hubspot.slack.client.models.blocks.objects.ConfirmationDialog;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContextActionsBlockTest {

  private static final ObjectMapper MAPPER = ObjectMapperUtils.mapper();
  private static final int FEEDBACK_BUTTONS_ONLY_INDEX = 0;
  private static final int ICON_BUTTON_ONLY_INDEX = 1;
  private static final int MIXED_ELEMENTS_INDEX = 2;
  private static final int WITH_BLOCK_ID_INDEX = 3;
  private static final int ICON_BUTTON_ALL_FIELDS_INDEX = 4;
  private static final int UNKNOWN_ELEMENT_INDEX = 5;
  private static ContextActionsBlock[] blocks;

  @BeforeClass
  public static void loadBlocks() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("context_actions_block.json");
    JsonNode root = MAPPER.readTree(rawJson);
    blocks = new ContextActionsBlock[root.size()];
    for (int i = 0; i < root.size(); i++) {
      blocks[i] = MAPPER.treeToValue(root.get(i), ContextActionsBlock.class);
    }
  }

  @Test
  public void itDeserializesAsCorrectBlockType() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("context_actions_block.json");
    JsonNode root = MAPPER.readTree(rawJson);
    Block block = MAPPER.treeToValue(root.get(0), Block.class);
    assertThat(block).isInstanceOf(ContextActionsBlock.class);
  }

  @Test
  public void itDeserializesFeedbackButtonsElement() {
    assertThat(blocks[FEEDBACK_BUTTONS_ONLY_INDEX].getElements())
      .containsExactly(
        FeedbackButtonsElement
          .builder()
          .setPositiveButton(
            FeedbackButton
              .builder()
              .setText(Text.of(TextType.PLAIN_TEXT, "Not so bad"))
              .setValue("positive_feedback")
              .setAccessibilityLabel("Mark this response as good")
              .build()
          )
          .setNegativeButton(
            FeedbackButton
              .builder()
              .setText(Text.of(TextType.PLAIN_TEXT, "Not so good"))
              .setValue("negative_feedback")
              .setAccessibilityLabel("Mark this response as bad")
              .build()
          )
          .setActionId("feedback_buttons_1")
          .build()
      );
  }

  @Test
  public void itDeserializesIconButtonElement() {
    assertThat(blocks[ICON_BUTTON_ONLY_INDEX].getElements())
      .containsExactly(
        IconButtonElement
          .builder()
          .setIcon("trash")
          .setText(Text.of(TextType.PLAIN_TEXT, "Delete"))
          .setActionId("delete_button")
          .setValue("delete_item")
          .build()
      );
  }

  @Test
  public void itDeserializesMixedElements() {
    assertThat(blocks[MIXED_ELEMENTS_INDEX].getElements()).hasSize(2);
    assertThat(blocks[MIXED_ELEMENTS_INDEX].getElements().get(0))
      .isInstanceOf(FeedbackButtonsElement.class);
    assertThat(blocks[MIXED_ELEMENTS_INDEX].getElements().get(1))
      .isInstanceOf(IconButtonElement.class);
  }

  @Test
  public void itDeserializesBlockId() {
    assertThat(blocks[WITH_BLOCK_ID_INDEX].getBlockId())
      .contains("context_actions_with_block_id");
  }

  @Test
  public void itDeserializesFeedbackButtonsWithoutOptionalFields() {
    ContextActionsElement element = blocks[WITH_BLOCK_ID_INDEX].getElements().get(0);
    assertThat(element)
      .isEqualTo(
        FeedbackButtonsElement
          .builder()
          .setPositiveButton(
            FeedbackButton
              .builder()
              .setText(Text.of(TextType.PLAIN_TEXT, "Good"))
              .setValue("good")
              .build()
          )
          .setNegativeButton(
            FeedbackButton
              .builder()
              .setText(Text.of(TextType.PLAIN_TEXT, "Bad"))
              .setValue("bad")
              .build()
          )
          .build()
      );
  }

  @Test
  public void itDeserializesIconButtonWithAllOptionalFields() {
    assertThat(blocks[ICON_BUTTON_ALL_FIELDS_INDEX].getElements())
      .containsExactly(
        IconButtonElement
          .builder()
          .setIcon("trash")
          .setText(Text.of(TextType.PLAIN_TEXT, "Delete"))
          .setConfirm(
            ConfirmationDialog.of(
              Text.of(TextType.PLAIN_TEXT, "Are you sure?"),
              Text.of(TextType.PLAIN_TEXT, "This cannot be undone"),
              Text.of(TextType.PLAIN_TEXT, "Yes"),
              Text.of(TextType.PLAIN_TEXT, "No")
            )
          )
          .setAccessibilityLabel("Delete this item")
          .addVisibleToUserIds("U123ABC", "U456DEF")
          .build()
      );
  }

  @Test
  public void itDeserializesUnknownElementAsUnknownContextActionsElement() {
    assertThat(blocks[UNKNOWN_ELEMENT_INDEX].getElements())
      .hasSize(1)
      .first()
      .isInstanceOf(UnknownContextActionsElement.class);
  }

  @Test
  public void itSerializesAndDeserializes() throws IOException {
    ContextActionsBlock original = ContextActionsBlock
      .builder()
      .addElements(
        FeedbackButtonsElement
          .builder()
          .setPositiveButton(
            FeedbackButton
              .builder()
              .setText(Text.of(TextType.PLAIN_TEXT, "Good"))
              .setValue("good")
              .build()
          )
          .setNegativeButton(
            FeedbackButton
              .builder()
              .setText(Text.of(TextType.PLAIN_TEXT, "Bad"))
              .setValue("bad")
              .build()
          )
          .setActionId("feedback_1")
          .build(),
        IconButtonElement
          .builder()
          .setIcon("trash")
          .setText(Text.of(TextType.PLAIN_TEXT, "Delete"))
          .setActionId("delete_1")
          .build()
      )
      .build();
    String serialized = MAPPER.writeValueAsString(original);
    ContextActionsBlock deserialized = MAPPER.readValue(
      serialized,
      ContextActionsBlock.class
    );
    assertThat(deserialized).isEqualTo(original);
  }
}

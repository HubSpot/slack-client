package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.blocks.elements.Button;
import com.hubspot.slack.client.models.blocks.elements.Image;
import com.hubspot.slack.client.models.blocks.objects.SlackIconObject;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;
import java.io.IOException;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CardBlockTest {

  private static final ObjectMapper MAPPER = ObjectMapperUtils.mapper();
  private static final int SLACK_ICON_WITH_ACTIONS_INDEX = 0;
  private static final int HERO_IMAGE_INDEX = 1;
  private static final int ICON_WITH_ACTIONS_INDEX = 2;
  private static final int TITLE_ONLY_INDEX = 3;
  private static final int ACTIONS_ONLY_INDEX = 4;
  private static Card[] blocks;

  @BeforeClass
  public static void loadBlocks() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("card_block.json");
    JsonNode root = MAPPER.readTree(rawJson);
    blocks = new Card[root.size()];
    for (int i = 0; i < root.size(); i++) {
      blocks[i] = MAPPER.treeToValue(root.get(i), Card.class);
    }
  }

  @Test
  public void itDeserializesAsCorrectBlockType() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("card_block.json");
    JsonNode root = MAPPER.readTree(rawJson);
    Block block = MAPPER.treeToValue(root.get(0), Block.class);
    assertThat(block).isInstanceOf(Card.class);
  }

  @Test
  public void itDeserializesSlackIcon() {
    Card block = blocks[SLACK_ICON_WITH_ACTIONS_INDEX];
    assertThat(block.getSlackIcon()).isPresent();
    SlackIconObject icon = block.getSlackIcon().get();
    assertThat(icon.getName()).isEqualTo("calendar");
    assertThat(icon.getType()).isEqualTo("icon");
  }

  @Test
  public void itDeserializesTextFields() {
    Card block = blocks[SLACK_ICON_WITH_ACTIONS_INDEX];
    assertThat(block.getTitle()).isPresent();
    assertThat(block.getTitle().get().getText()).isEqualTo("Meeting title");
    assertThat(block.getTitle().get().getType()).isEqualTo(TextType.MARKDOWN);
    assertThat(block.getSubtitle()).isPresent();
    assertThat(block.getSubtitle().get().getText()).isEqualTo("This is a subtitle");
    assertThat(block.getBody()).isPresent();
    assertThat(block.getBody().get().getText()).isEqualTo("This is the body text.");
  }

  @Test
  public void itDeserializesActions() {
    Card block = blocks[SLACK_ICON_WITH_ACTIONS_INDEX];
    assertThat(block.getActions()).isPresent();
    List<?> actions = block.getActions().get();
    assertThat(actions).hasSize(3);
    assertThat(actions.get(0)).isInstanceOf(Button.class);
    assertThat(actions.get(1)).isInstanceOf(Button.class);
    assertThat(actions.get(2)).isInstanceOf(Button.class);
  }

  @Test
  public void itDeserializesHeroImage() {
    Card block = blocks[HERO_IMAGE_INDEX];
    assertThat(block.getHeroImage()).isPresent();
    Image heroImage = block.getHeroImage().get();
    assertThat(heroImage.getImageUrl()).isEqualTo("https://example.com/hero.png");
    assertThat(heroImage.getAltText()).isEqualTo("Sample hero image");
  }

  @Test
  public void itDeserializesIconImage() {
    Card block = blocks[ICON_WITH_ACTIONS_INDEX];
    assertThat(block.getIcon()).isPresent();
    Image icon = block.getIcon().get();
    assertThat(icon.getImageUrl()).isEqualTo("https://example.com/icon.png");
    assertThat(icon.getAltText()).isEqualTo("Icon");
  }

  @Test
  public void itDeserializesTitleOnlyCard() {
    Card block = blocks[TITLE_ONLY_INDEX];
    assertThat(block.getTitle()).isPresent();
    assertThat(block.getHeroImage()).isEmpty();
    assertThat(block.getBody()).isEmpty();
    assertThat(block.getActions()).isEmpty();
  }

  @Test
  public void itDeserializesActionsOnlyCard() {
    Card block = blocks[ACTIONS_ONLY_INDEX];
    assertThat(block.getActions()).isPresent();
    assertThat(block.getTitle()).isEmpty();
    assertThat(block.getHeroImage()).isEmpty();
    assertThat(block.getBody()).isEmpty();
  }

  @Test
  public void itSerializesAndDeserializes() throws IOException {
    Card original = Card
      .builder()
      .setTitle(Text.of(TextType.MARKDOWN, "Test Title"))
      .setBody(Text.of(TextType.MARKDOWN, "Test body"))
      .build();
    String serialized = MAPPER.writeValueAsString(original);
    Card deserialized = MAPPER.readValue(serialized, Card.class);
    assertThat(deserialized).isEqualTo(original);
  }

  @Test
  public void itFailsWhenNoRequiredFieldsPresent() {
    assertThatThrownBy(() -> Card.builder().build())
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("at least one of");
  }

  @Test
  public void itFailsWhenIconAndSlackIconBothPresent() {
    assertThatThrownBy(() ->
        Card
          .builder()
          .setTitle(Text.of(TextType.MARKDOWN, "Title"))
          .setIcon(Image.of("https://example.com/icon.png", "icon"))
          .setSlackIcon(SlackIconObject.of("rocket"))
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("cannot have both icon and slack_icon");
  }

  @Test
  public void itFailsWhenTitleExceedsMaxLength() {
    String longTitle = "a".repeat(151);
    assertThatThrownBy(() ->
        Card.builder().setTitle(Text.of(TextType.MARKDOWN, longTitle)).build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("title cannot exceed");
  }

  @Test
  public void itFailsWhenBodyExceedsMaxLength() {
    String longBody = "a".repeat(201);
    assertThatThrownBy(() ->
        Card.builder().setBody(Text.of(TextType.MARKDOWN, longBody)).build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("body cannot exceed");
  }

  @Test
  public void itFailsWhenActionsExceedMaxCount() {
    Button button = Button
      .builder()
      .setText(Text.of(TextType.PLAIN_TEXT, "Click"))
      .setActionId("btn")
      .build();
    assertThatThrownBy(() ->
        Card
          .builder()
          .setTitle(Text.of(TextType.MARKDOWN, "Title"))
          .setActions(List.of(button, button, button, button))
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("cannot have more than");
  }
}

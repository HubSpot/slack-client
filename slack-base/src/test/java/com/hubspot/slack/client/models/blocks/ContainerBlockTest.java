package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContainerBlockTest {

  private static final ObjectMapper MAPPER = ObjectMapperUtils.mapper();
  private static final int FULL_BLOCK_INDEX = 0;
  private static final int MINIMAL_BLOCK_INDEX = 1;
  private static final int RICH_TEXT_TITLE_INDEX = 2;
  private static final int HEADER_DIVIDER_INDEX = 3;
  private static ContainerBlock[] blocks;

  @BeforeClass
  public static void loadBlocks() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("container_block.json");
    JsonNode root = MAPPER.readTree(rawJson);
    blocks = new ContainerBlock[root.size()];
    for (int i = 0; i < root.size(); i++) {
      blocks[i] = MAPPER.treeToValue(root.get(i), ContainerBlock.class);
    }
  }

  @Test
  public void itDeserializesAsCorrectBlockType() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("container_block.json");
    JsonNode root = MAPPER.readTree(rawJson);
    Block block = MAPPER.treeToValue(root.get(0), Block.class);
    assertThat(block).isInstanceOf(ContainerBlock.class);
  }

  @Test
  public void itDeserializesAllFields() {
    ContainerBlock block = blocks[FULL_BLOCK_INDEX];
    assertThat(block.getBlockId()).hasValue("block123");
    assertThat(block.getTitle()).isPresent();
    assertThat(block.getTitle().get().getText()).isEqualTo("My Container");
    assertThat(block.getTitle().get().getType()).isEqualTo(TextType.PLAIN_TEXT);
    assertThat(block.getSubtitle()).isPresent();
    assertThat(block.getSubtitle().get().getText()).isEqualTo("A subtitle here");
    assertThat(block.getIcon()).isPresent();
    assertThat(block.getIcon().get().getImageUrl())
      .isEqualTo("https://example.com/icon.png");
    assertThat(block.getWidth()).hasValue(ContainerBlockWidth.WIDE);
    assertThat(block.isCollapsible()).hasValue(true);
    assertThat(block.isDefaultCollapsed()).hasValue(false);
    assertThat(block.getHasHeaderDivider()).isEmpty();
    assertThat(block.getChildBlocks()).hasSize(2);
    assertThat(block.getChildBlocks().get(0)).isInstanceOf(Section.class);
    assertThat(block.getChildBlocks().get(1)).isInstanceOf(Divider.class);
  }

  @Test
  public void itDeserializesMinimalBlock() {
    ContainerBlock block = blocks[MINIMAL_BLOCK_INDEX];
    assertThat(block.getTitle()).isPresent();
    assertThat(block.getTitle().get().getText()).isEqualTo("Minimal Container");
    assertThat(block.getChildBlocks()).hasSize(1);
    assertThat(block.getRichTextTitle()).isEmpty();
    assertThat(block.getSubtitle()).isEmpty();
    assertThat(block.getIcon()).isEmpty();
    assertThat(block.getWidth()).isEmpty();
    assertThat(block.isCollapsible()).isEmpty();
    assertThat(block.isDefaultCollapsed()).isEmpty();
    assertThat(block.getHasHeaderDivider()).isEmpty();
    assertThat(block.getBlockId()).isEmpty();
  }

  @Test
  public void itDeserializesHasHeaderDivider() {
    ContainerBlock block = blocks[HEADER_DIVIDER_INDEX];
    assertThat(block.getHasHeaderDivider()).hasValue(true);
    assertThat(block.isCollapsible()).isEmpty();
  }

  @Test
  public void itDeserializesRichTextTitle() {
    ContainerBlock block = blocks[RICH_TEXT_TITLE_INDEX];
    assertThat(block.getTitle()).isEmpty();
    assertThat(block.getRichTextTitle()).isPresent();
  }

  @Test
  public void itSerializesAndDeserializes() throws IOException {
    ContainerBlock original = ContainerBlock
      .builder()
      .setTitle(Text.of(TextType.PLAIN_TEXT, "Test"))
      .addChildBlocks(Divider.builder().build())
      .build();
    String serialized = MAPPER.writeValueAsString(original);
    ContainerBlock deserialized = MAPPER.readValue(serialized, ContainerBlock.class);
    assertThat(deserialized).isEqualTo(original);
  }

  @Test
  public void itFailsWhenNeitherTitleNorRichTextTitlePresent() {
    assertThatThrownBy(() ->
        ContainerBlock.builder().addChildBlocks(Divider.builder().build()).build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("must have either title or rich_text_title");
  }

  @Test
  public void itFailsWhenTitleTypeIsNotPlainText() {
    assertThatThrownBy(() ->
        ContainerBlock
          .builder()
          .setTitle(Text.of(TextType.MARKDOWN, "Title"))
          .addChildBlocks(Divider.builder().build())
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("plain_text formatting");
  }

  @Test
  public void itFailsWhenTitleExceedsMaxLength() {
    String longTitle = "a".repeat(151);
    assertThatThrownBy(() ->
        ContainerBlock
          .builder()
          .setTitle(Text.of(TextType.PLAIN_TEXT, longTitle))
          .addChildBlocks(Divider.builder().build())
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("title cannot exceed");
  }

  @Test
  public void itFailsWhenSubtitleExceedsMaxLength() {
    String longSubtitle = "a".repeat(151);
    assertThatThrownBy(() ->
        ContainerBlock
          .builder()
          .setTitle(Text.of(TextType.PLAIN_TEXT, "Title"))
          .setSubtitle(Text.of(TextType.PLAIN_TEXT, longSubtitle))
          .addChildBlocks(Divider.builder().build())
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("subtitle cannot exceed");
  }

  @Test
  public void itFailsWhenChildBlocksExceedMaxCount() {
    ImmutableList.Builder<Block> childBlocks = ImmutableList.builder();
    for (int i = 0; i < 11; i++) {
      childBlocks.add(Divider.builder().build());
    }
    assertThatThrownBy(() ->
        ContainerBlock
          .builder()
          .setTitle(Text.of(TextType.PLAIN_TEXT, "Title"))
          .setChildBlocks(childBlocks.build())
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("child_blocks cannot exceed");
  }

  @Test
  public void itFailsWhenDefaultCollapsedWithoutIsCollapsible() {
    assertThatThrownBy(() ->
        ContainerBlock
          .builder()
          .setTitle(Text.of(TextType.PLAIN_TEXT, "Title"))
          .addChildBlocks(Divider.builder().build())
          .setDefaultCollapsed(true)
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("default_collapsed requires is_collapsible");
  }

  @Test
  public void itFailsWhenHasHeaderDividerOnCollapsibleBlock() {
    assertThatThrownBy(() ->
        ContainerBlock
          .builder()
          .setTitle(Text.of(TextType.PLAIN_TEXT, "Title"))
          .addChildBlocks(Divider.builder().build())
          .setCollapsible(true)
          .setHasHeaderDivider(true)
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("has_header_divider does not apply to collapsible blocks");
  }

  @Test
  public void itFailsWhenChildBlockIsContainerBlock() {
    ContainerBlock inner = ContainerBlock
      .builder()
      .setTitle(Text.of(TextType.PLAIN_TEXT, "Inner"))
      .addChildBlocks(Divider.builder().build())
      .build();
    assertThatThrownBy(() ->
        ContainerBlock
          .builder()
          .setTitle(Text.of(TextType.PLAIN_TEXT, "Outer"))
          .addChildBlocks(inner)
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("cannot contain container blocks");
  }
}

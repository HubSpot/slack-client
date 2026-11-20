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

public class LayoutBlockTest {

  private static final ObjectMapper MAPPER = ObjectMapperUtils.mapper();
  private static final int FULL_BLOCK_INDEX = 0;
  private static final int MINIMAL_BLOCK_INDEX = 1;
  private static LayoutBlock[] blocks;

  @BeforeClass
  public static void loadBlocks() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("layout_block.json");
    JsonNode root = MAPPER.readTree(rawJson);
    blocks = new LayoutBlock[root.size()];
    for (int i = 0; i < root.size(); i++) {
      blocks[i] = MAPPER.treeToValue(root.get(i), LayoutBlock.class);
    }
  }

  @Test
  public void itDeserializesAsCorrectBlockType() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("layout_block.json");
    JsonNode root = MAPPER.readTree(rawJson);
    Block block = MAPPER.treeToValue(root.get(0), Block.class);
    assertThat(block).isInstanceOf(LayoutBlock.class);
  }

  @Test
  public void itDeserializesAllFields() {
    LayoutBlock block = blocks[FULL_BLOCK_INDEX];
    assertThat(block.getBlockId()).hasValue("layout123");
    assertThat(block.getLabel()).isEqualTo("Portal selector");
    assertThat(block.isLabelVisible()).hasValue(true);
    assertThat(block.getGridLines()).hasValue(LayoutGridLines.ROWS);
    assertThat(block.getRows()).hasSize(2);

    LayoutRow firstRow = block.getRows().get(0);
    assertThat(firstRow.getItems()).hasSize(2);

    LayoutItem firstItem = firstRow.getItems().get(0);
    assertThat(firstItem.getWidth()).hasValue(LayoutItemWidth.WIDE);
    assertThat(firstItem.getHorizontalAlignment()).isEmpty();
    assertThat(firstItem.getBlock()).isInstanceOf(Section.class);

    LayoutItem secondItem = firstRow.getItems().get(1);
    assertThat(secondItem.getWidth()).hasValue(LayoutItemWidth.NARROW);
    assertThat(secondItem.getHorizontalAlignment())
      .hasValue(LayoutHorizontalAlignment.END);
    assertThat(secondItem.getBlock()).isInstanceOf(Actions.class);

    LayoutRow secondRow = block.getRows().get(1);
    assertThat(secondRow.getItems()).hasSize(1);
    assertThat(secondRow.getItems().get(0).getBlock()).isInstanceOf(Divider.class);
  }

  @Test
  public void itDeserializesMinimalBlock() {
    LayoutBlock block = blocks[MINIMAL_BLOCK_INDEX];
    assertThat(block.getLabel()).isEqualTo("Minimal layout");
    assertThat(block.getBlockId()).isEmpty();
    assertThat(block.isLabelVisible()).isEmpty();
    assertThat(block.getGridLines()).isEmpty();
    assertThat(block.getRows()).hasSize(1);
    assertThat(block.getRows().get(0).getItems()).hasSize(1);
  }

  @Test
  public void itSerializesAndDeserializes() throws IOException {
    LayoutBlock original = LayoutBlock
      .builder()
      .setLabel("Round trip")
      .setLabelVisible(true)
      .setGridLines(LayoutGridLines.OUTER)
      .addRows(
        LayoutRow
          .builder()
          .addItems(
            LayoutItem
              .builder()
              .setBlock(Section.of(Text.of(TextType.MARKDOWN, "Hello")))
              .setWidth(LayoutItemWidth.STANDARD)
              .setHorizontalAlignment(LayoutHorizontalAlignment.START)
              .build()
          )
          .addItems(LayoutItem.builder().setBlock(Divider.builder().build()).build())
          .build()
      )
      .build();
    String serialized = MAPPER.writeValueAsString(original);
    LayoutBlock deserialized = MAPPER.readValue(serialized, LayoutBlock.class);
    assertThat(deserialized).isEqualTo(original);
  }

  @Test
  public void itOmitsAbsentOptionalItemFields() throws IOException {
    // Slack rejects "width": null / "horizontal_alignment": null — absent optionals must be omitted.
    String serialized = MAPPER.writeValueAsString(minimalItem());
    assertThat(serialized).doesNotContain("width");
    assertThat(serialized).doesNotContain("horizontal_alignment");
  }

  @Test
  public void itSerializesWireShape() throws IOException {
    LayoutBlock block = LayoutBlock
      .builder()
      .setLabel("Wire shape")
      .setLabelVisible(true)
      .setGridLines(LayoutGridLines.OUTER)
      .addRows(minimalRow())
      .build();
    String json = MAPPER.writeValueAsString(block);
    assertThat(json).contains("\"is_label_visible\":true");
    assertThat(json).contains("\"grid_lines\":\"outer\"");
    assertThat(json).doesNotContain("null");
    // fallback is empty -> omitted
    assertThat(json).doesNotContain("fallback");
  }

  @Test
  public void itFailsWhenLabelBlank() {
    assertThatThrownBy(() ->
        LayoutBlock.builder().setLabel("   ").addRows(minimalRow()).build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("label is required");
  }

  @Test
  public void itAcceptsAllowedFallbackTypes() {
    LayoutBlock block = LayoutBlock
      .builder()
      .setLabel("With fallback")
      .addRows(minimalRow())
      .addFallback(Section.of(Text.of(TextType.MARKDOWN, "fallback text")))
      .addFallback(Divider.builder().build())
      .build();
    assertThat(block.getFallback()).hasSize(2);
  }

  @Test
  public void itFailsWhenFallbackTypeNotAllowed() {
    assertThatThrownBy(() ->
        LayoutBlock
          .builder()
          .setLabel("Bad fallback")
          .addRows(minimalRow())
          .addFallback(Actions.builder().build())
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("fallback does not support block type 'actions'");
  }

  @Test
  public void itFailsWhenLabelExceedsMaxLength() {
    String longLabel = "a".repeat(151);
    assertThatThrownBy(() ->
        LayoutBlock.builder().setLabel(longLabel).addRows(minimalRow()).build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("label cannot exceed");
  }

  @Test
  public void itFailsWhenRowsEmpty() {
    assertThatThrownBy(() -> LayoutBlock.builder().setLabel("Label").build())
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("must contain between 1 and 5 rows");
  }

  @Test
  public void itFailsWhenRowsExceedMaxCount() {
    ImmutableList.Builder<LayoutRow> rows = ImmutableList.builder();
    for (int i = 0; i < 6; i++) {
      rows.add(minimalRow());
    }
    assertThatThrownBy(() ->
        LayoutBlock.builder().setLabel("Label").setRows(rows.build()).build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("must contain between 1 and 5 rows");
  }

  @Test
  public void itFailsWhenRowItemsEmpty() {
    assertThatThrownBy(() -> LayoutRow.builder().build())
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("must contain between 1 and 3 items");
  }

  @Test
  public void itFailsWhenRowItemsExceedMaxCount() {
    assertThatThrownBy(() ->
        LayoutRow
          .builder()
          .addItems(minimalItem())
          .addItems(minimalItem())
          .addItems(minimalItem())
          .addItems(minimalItem())
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("must contain between 1 and 3 items");
  }

  private static LayoutItem minimalItem() {
    return LayoutItem.builder().setBlock(Divider.builder().build()).build();
  }

  private static LayoutRow minimalRow() {
    return LayoutRow.builder().addItems(minimalItem()).build();
  }
}

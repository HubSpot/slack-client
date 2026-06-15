package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextSection;
import com.hubspot.slack.client.models.blocks.table.RawTextTableCell;
import com.hubspot.slack.client.models.blocks.table.RichTextTableCell;
import com.hubspot.slack.client.models.blocks.table.TableCell;
import com.hubspot.slack.client.models.blocks.table.TableColumnSetting;
import com.hubspot.slack.client.models.blocks.table.UnknownTableCell;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class TableBlockTest {

  private static final ObjectMapper MAPPER = ObjectMapperUtils.mapper();
  private static final int RAW_TEXT_INDEX = 0;
  private static final int RICH_TEXT_INDEX = 1;
  private static final int COLUMN_SETTINGS_INDEX = 2;
  private static final int BLOCK_ID_INDEX = 3;
  private static final int UNKNOWN_CELL_INDEX = 4;
  private static Table[] blocks;

  @BeforeClass
  public static void loadBlocks() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("table_block.json");
    JsonNode root = MAPPER.readTree(rawJson);
    blocks = new Table[root.size()];
    for (int i = 0; i < root.size(); i++) {
      blocks[i] = MAPPER.treeToValue(root.get(i), Table.class);
    }
  }

  @Test
  public void itDeserializesAsCorrectBlockType() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("table_block.json");
    JsonNode root = MAPPER.readTree(rawJson);
    Block block = MAPPER.treeToValue(root.get(0), Block.class);
    assertThat(block).isInstanceOf(Table.class);
  }

  @Test
  public void itDeserializesRawTextCells() {
    Table block = blocks[RAW_TEXT_INDEX];
    assertThat(block.getRows()).hasSize(2);
    assertThat(block.getRows().get(0))
      .containsExactly(RawTextTableCell.of("Name"), RawTextTableCell.of("Department"));
    assertThat(block.getRows().get(1))
      .containsExactly(RawTextTableCell.of("Alice"), RawTextTableCell.of("Engineering"));
  }

  @Test
  public void itDeserializesRichTextCells() {
    Table block = blocks[RICH_TEXT_INDEX];
    assertThat(block.getRows()).hasSize(1);
    assertThat(block.getRows().get(0).get(0))
      .asInstanceOf(type(RichTextTableCell.class))
      .satisfies(cell -> {
        assertThat(cell.getElements()).hasSize(1);
        assertThat(cell.getElements().get(0)).isInstanceOf(RichTextSection.class);
      });
  }

  @Test
  public void itDeserializesColumnSettings() {
    Table block = blocks[COLUMN_SETTINGS_INDEX];
    assertThat(block.getColumnSettings()).hasSize(3);
    assertThat(block.getColumnSettings().get(0))
      .isEqualTo(TableColumnSetting.builder().setAlign("left").build());
    assertThat(block.getColumnSettings().get(1))
      .isEqualTo(
        TableColumnSetting.builder().setAlign("center").setWrapped(true).build()
      );
    assertThat(block.getColumnSettings().get(2))
      .isEqualTo(TableColumnSetting.builder().setAlign("right").build());
  }

  @Test
  public void itDeserializesBlockId() {
    assertThat(blocks[BLOCK_ID_INDEX].getBlockId()).contains("my_table_block");
  }

  @Test
  public void itDeserializesUnknownCellAsUnknownTableCell() {
    assertThat(blocks[UNKNOWN_CELL_INDEX].getRows()).hasSize(1);
    assertThat(blocks[UNKNOWN_CELL_INDEX].getRows().get(0))
      .hasSize(1)
      .first()
      .isInstanceOf(UnknownTableCell.class);
  }

  @Test
  public void itSerializesAndDeserializes() throws IOException {
    Table original = Table
      .builder()
      .addRows(
        ImmutableList.of(RawTextTableCell.of("Name"), RawTextTableCell.of("Score"))
      )
      .addRows(ImmutableList.of(RawTextTableCell.of("Alice"), RawTextTableCell.of("100")))
      .addColumnSettings(TableColumnSetting.builder().setAlign("left").build())
      .addColumnSettings(
        TableColumnSetting.builder().setAlign("right").setWrapped(true).build()
      )
      .build();
    String serialized = MAPPER.writeValueAsString(original);
    Table deserialized = MAPPER.readValue(serialized, Table.class);
    assertThat(deserialized).isEqualTo(original);
  }

  @Test
  public void itFailsToBuildWithTooManyRows() {
    Table.Builder builder = Table.builder();
    for (int i = 0; i <= BlockElementLengthLimits.MAX_TABLE_ROWS.getLimit(); i++) {
      builder.addRows(ImmutableList.of(RawTextTableCell.of("cell")));
    }
    try {
      builder.build();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("100");
      return;
    }
    fail("Expected IllegalStateException for too many rows");
  }

  @Test
  public void itFailsToBuildWithTooManyCellsInRow() {
    ImmutableList.Builder<TableCell> rowBuilder = ImmutableList.builder();
    for (int i = 0; i <= BlockElementLengthLimits.MAX_TABLE_COLUMNS.getLimit(); i++) {
      rowBuilder.add(RawTextTableCell.of("cell"));
    }
    try {
      Table.builder().addRows(rowBuilder.build()).build();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("20");
      return;
    }
    fail("Expected IllegalStateException for too many cells in row");
  }

  @Test
  public void itFailsToBuildWithTooManyColumnSettings() {
    Table.Builder builder = Table.builder();
    builder.addRows(ImmutableList.of(RawTextTableCell.of("cell")));
    for (int i = 0; i <= BlockElementLengthLimits.MAX_TABLE_COLUMNS.getLimit(); i++) {
      builder.addColumnSettings(TableColumnSetting.builder().setAlign("left").build());
    }
    try {
      builder.build();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("20");
      return;
    }
    fail("Expected IllegalStateException for too many column settings");
  }

  @Test
  public void itFailsToBuildWithBlockIdExceedingMaxLength() {
    try {
      Table
        .builder()
        .addRows(ImmutableList.of(RawTextTableCell.of("cell")))
        .setBlockId(
          "a".repeat(BlockElementLengthLimits.MAX_TABLE_BLOCK_ID_LENGTH.getLimit() + 1)
        )
        .build();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("255");
      return;
    }
    fail("Expected IllegalStateException for block_id exceeding max length");
  }
}

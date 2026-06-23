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
import com.hubspot.slack.client.models.blocks.table.DataTableCell;
import com.hubspot.slack.client.models.blocks.table.RawNumberTableCell;
import com.hubspot.slack.client.models.blocks.table.RawTextTableCell;
import com.hubspot.slack.client.models.blocks.table.RichTextTableCell;
import com.hubspot.slack.client.models.blocks.table.UnknownTableCell;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataTableBlockTest {

  private static final ObjectMapper MAPPER = ObjectMapperUtils.mapper();
  private static final int RICH_TEXT_BODY_INDEX = 0;
  private static final int RAW_NUMBER_INDEX = 1;
  private static final int WITH_OPTIONS_INDEX = 2;
  private static final int UNKNOWN_CELL_INDEX = 3;
  private static DataTable[] blocks;

  @BeforeClass
  public static void loadBlocks() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("data_table_block.json");
    JsonNode root = MAPPER.readTree(rawJson);
    blocks = new DataTable[root.size()];
    for (int i = 0; i < root.size(); i++) {
      blocks[i] = MAPPER.treeToValue(root.get(i), DataTable.class);
    }
  }

  @Test
  public void itDeserializesAsCorrectBlockType() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("data_table_block.json");
    JsonNode root = MAPPER.readTree(rawJson);
    Block block = MAPPER.treeToValue(root.get(0), Block.class);
    assertThat(block).isInstanceOf(DataTable.class);
  }

  @Test
  public void itDeserializesCaption() {
    assertThat(blocks[RICH_TEXT_BODY_INDEX].getCaption()).isEqualTo("Team Directory");
  }

  @Test
  public void itDeserializesHeaderRowAsRawTextCells() {
    ImmutableList<DataTableCell> headerRow =
      blocks[RICH_TEXT_BODY_INDEX].getRows().get(0);
    assertThat(headerRow)
      .containsExactly(
        RawTextTableCell.of("Name"),
        RawTextTableCell.of("Department"),
        RawTextTableCell.of("Badge")
      );
  }

  @Test
  public void itDeserializesRichTextBodyCell() {
    ImmutableList<DataTableCell> bodyRow = blocks[RICH_TEXT_BODY_INDEX].getRows().get(1);
    assertThat(bodyRow.get(2))
      .asInstanceOf(type(RichTextTableCell.class))
      .satisfies(cell -> {
        assertThat(cell.getElements()).hasSize(1);
        assertThat(cell.getElements().get(0)).isInstanceOf(RichTextSection.class);
      });
  }

  @Test
  public void itDeserializesRawNumberCells() {
    ImmutableList<DataTableCell> bodyRow = blocks[RAW_NUMBER_INDEX].getRows().get(1);
    assertThat(bodyRow.get(1)).isEqualTo(RawNumberTableCell.of(95.0, "95"));
    assertThat(bodyRow.get(2)).isEqualTo(RawNumberTableCell.of(10.5, "10.5"));
  }

  @Test
  public void itDeserializesRawNumberValueAndText() {
    ImmutableList<DataTableCell> bodyRow = blocks[RAW_NUMBER_INDEX].getRows().get(1);
    assertThat(bodyRow.get(1))
      .asInstanceOf(type(RawNumberTableCell.class))
      .satisfies(cell -> {
        assertThat(cell.getValue()).isEqualTo(95.0);
        assertThat(cell.getText()).isEqualTo("95");
      });
  }

  @Test
  public void itDeserializesBlockId() {
    assertThat(blocks[WITH_OPTIONS_INDEX].getBlockId()).contains("my_data_table");
  }

  @Test
  public void itDeserializesPageSize() {
    assertThat(blocks[WITH_OPTIONS_INDEX].getPageSize()).contains(10);
  }

  @Test
  public void itDeserializesRowHeaderColumnIndex() {
    assertThat(blocks[WITH_OPTIONS_INDEX].getRowHeaderColumnIndex()).contains(1);
  }

  @Test
  public void itDeserializesUnknownCellAsUnknownTableCell() {
    assertThat(blocks[UNKNOWN_CELL_INDEX].getRows().get(1))
      .hasSize(1)
      .first()
      .isInstanceOf(UnknownTableCell.class);
  }

  @Test
  public void itSerializesAndDeserializes() throws IOException {
    DataTable original = DataTable
      .builder()
      .setCaption("Scores")
      .addRows(
        ImmutableList.of(RawTextTableCell.of("Player"), RawTextTableCell.of("Score"))
      )
      .addRows(
        ImmutableList.of(RawTextTableCell.of("Alice"), RawNumberTableCell.of(95.0, "95"))
      )
      .build();
    String serialized = MAPPER.writeValueAsString(original);
    DataTable deserialized = MAPPER.readValue(serialized, DataTable.class);
    assertThat(deserialized).isEqualTo(original);
  }

  @Test
  public void itFailsToBuildWithTooFewRows() {
    try {
      DataTable
        .builder()
        .setCaption("Empty")
        .addRows(ImmutableList.of(RawTextTableCell.of("Header")))
        .build();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("2");
      return;
    }
    fail("Expected IllegalStateException for fewer than 2 rows");
  }

  @Test
  public void itFailsToBuildWithTooManyRows() {
    DataTable.Builder builder = DataTable.builder().setCaption("Big");
    builder.addRows(ImmutableList.of(RawTextTableCell.of("Header")));
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
  public void itFailsToBuildWithTooManyColumns() {
    ImmutableList.Builder<DataTableCell> rowBuilder = ImmutableList.builder();
    for (int i = 0; i <= BlockElementLengthLimits.MAX_TABLE_COLUMNS.getLimit(); i++) {
      rowBuilder.add(RawTextTableCell.of("cell"));
    }
    ImmutableList<DataTableCell> wideRow = rowBuilder.build();
    try {
      DataTable.builder().setCaption("Wide").addRows(wideRow).addRows(wideRow).build();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("20");
      return;
    }
    fail("Expected IllegalStateException for too many columns");
  }

  @Test
  public void itFailsToBuildWithInconsistentColumnCounts() {
    try {
      DataTable
        .builder()
        .setCaption("Inconsistent")
        .addRows(ImmutableList.of(RawTextTableCell.of("A"), RawTextTableCell.of("B")))
        .addRows(ImmutableList.of(RawTextTableCell.of("only one")))
        .build();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("same number of columns");
      return;
    }
    fail("Expected IllegalStateException for inconsistent column counts");
  }

  @Test
  public void itFailsToBuildWithNonRawTextHeaderCell() {
    try {
      DataTable
        .builder()
        .setCaption("Bad Header")
        .addRows(
          ImmutableList.of(RawTextTableCell.of("OK"), RawNumberTableCell.of(1.0, "1"))
        )
        .addRows(
          ImmutableList.of(RawTextTableCell.of("body"), RawTextTableCell.of("body"))
        )
        .build();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("raw_text");
      return;
    }
    fail("Expected IllegalStateException for non-raw_text header cell");
  }

  @Test
  public void itFailsToBuildWithInvalidPageSizeTooLow() {
    try {
      DataTable
        .builder()
        .setCaption("Bad Page")
        .addRows(ImmutableList.of(RawTextTableCell.of("Header")))
        .addRows(ImmutableList.of(RawTextTableCell.of("body")))
        .setPageSize(0)
        .build();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("between 1");
      return;
    }
    fail("Expected IllegalStateException for page_size of 0");
  }

  @Test
  public void itFailsToBuildWithInvalidPageSizeTooHigh() {
    try {
      DataTable
        .builder()
        .setCaption("Bad Page")
        .addRows(ImmutableList.of(RawTextTableCell.of("Header")))
        .addRows(ImmutableList.of(RawTextTableCell.of("body")))
        .setPageSize(BlockElementLengthLimits.MAX_DATA_TABLE_PAGE_SIZE.getLimit() + 1)
        .build();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("100");
      return;
    }
    fail("Expected IllegalStateException for page_size exceeding maximum");
  }

  @Test
  public void itFailsToBuildWithBlockIdExceedingMaxLength() {
    try {
      DataTable
        .builder()
        .setCaption("Long ID")
        .addRows(ImmutableList.of(RawTextTableCell.of("Header")))
        .addRows(ImmutableList.of(RawTextTableCell.of("body")))
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

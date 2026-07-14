package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.table.DataTableCell;
import com.hubspot.slack.client.models.blocks.table.RawTextTableCell;
import com.hubspot.slack.client.models.blocks.table.deserializer.NullSafeDataTableCellRowDeserializer;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface DataTableIF extends Block {
  String TYPE = "data_table";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter(order = 1)
  Optional<String> getCaption();

  @Value.Parameter(order = 2)
  @JsonDeserialize(contentUsing = NullSafeDataTableCellRowDeserializer.class)
  ImmutableList<ImmutableList<DataTableCell>> getRows();

  @JsonProperty("page_size")
  Optional<Integer> getPageSize();

  @JsonProperty("row_header_column_index")
  Optional<Integer> getRowHeaderColumnIndex();

  @Check
  default void check() {
    Preconditions.checkState(
      getRows().size() >= BlockElementLengthLimits.MIN_DATA_TABLE_ROWS.getLimit(),
      "A data_table block must have at least %s rows (1 header + 1 body)",
      BlockElementLengthLimits.MIN_DATA_TABLE_ROWS.getLimit()
    );
    Preconditions.checkState(
      getRows().size() <= BlockElementLengthLimits.MAX_TABLE_ROWS.getLimit(),
      "A data_table block cannot have more than %s rows",
      BlockElementLengthLimits.MAX_TABLE_ROWS.getLimit()
    );
    getRows()
      .forEach(row ->
        Preconditions.checkState(
          row.size() <= BlockElementLengthLimits.MAX_TABLE_COLUMNS.getLimit(),
          "A data_table row cannot have more than %s columns",
          BlockElementLengthLimits.MAX_TABLE_COLUMNS.getLimit()
        )
      );
    int columnCount = getRows().get(0).size();
    getRows()
      .forEach(row ->
        Preconditions.checkState(
          row.size() == columnCount,
          "All rows in a data_table block must have the same number of columns"
        )
      );
    getRows()
      .get(0)
      .forEach(cell ->
        Preconditions.checkState(
          cell instanceof RawTextTableCell,
          "Header row cells must be of type raw_text"
        )
      );
    getPageSize()
      .ifPresent(pageSize ->
        Preconditions.checkState(
          pageSize >= 1 &&
          pageSize <= BlockElementLengthLimits.MAX_DATA_TABLE_PAGE_SIZE.getLimit(),
          "page_size must be between 1 and %s",
          BlockElementLengthLimits.MAX_DATA_TABLE_PAGE_SIZE.getLimit()
        )
      );
    getBlockId()
      .ifPresent(blockId ->
        Preconditions.checkState(
          blockId.length() <=
          BlockElementLengthLimits.MAX_TABLE_BLOCK_ID_LENGTH.getLimit(),
          "block_id cannot exceed %s characters",
          BlockElementLengthLimits.MAX_TABLE_BLOCK_ID_LENGTH.getLimit()
        )
      );
  }
}

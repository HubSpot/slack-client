package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.table.TableCell;
import com.hubspot.slack.client.models.blocks.table.TableColumnSetting;
import com.hubspot.slack.client.models.blocks.table.deserializer.NullSafeTableCellRowDeserializer;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface TableIF extends Block {
  String TYPE = "table";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter
  @JsonDeserialize(contentUsing = NullSafeTableCellRowDeserializer.class)
  ImmutableList<ImmutableList<TableCell>> getRows();

  ImmutableList<TableColumnSetting> getColumnSettings();

  @Check
  default void check() {
    Preconditions.checkState(
      getRows().size() <= BlockElementLengthLimits.MAX_TABLE_ROWS.getLimit(),
      "A table block cannot have more than %s rows",
      BlockElementLengthLimits.MAX_TABLE_ROWS.getLimit()
    );
    getRows()
      .forEach(row ->
        Preconditions.checkState(
          row.size() <= BlockElementLengthLimits.MAX_TABLE_COLUMNS.getLimit(),
          "A table row cannot have more than %s cells",
          BlockElementLengthLimits.MAX_TABLE_COLUMNS.getLimit()
        )
      );
    Preconditions.checkState(
      getColumnSettings().size() <= BlockElementLengthLimits.MAX_TABLE_COLUMNS.getLimit(),
      "A table block cannot have more than %s column settings",
      BlockElementLengthLimits.MAX_TABLE_COLUMNS.getLimit()
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

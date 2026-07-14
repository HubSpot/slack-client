package com.hubspot.slack.client.models.blocks.table.deserializer;

import com.hubspot.slack.client.models.blocks.table.DataTableCell;
import com.hubspot.slack.client.models.blocks.table.RawTextTableCell;

public class NullSafeDataTableCellRowDeserializer
  extends AbstractNullSafeTableCellRowDeserializer<DataTableCell> {

  public NullSafeDataTableCellRowDeserializer() {
    super(DataTableCell.class);
  }

  @Override
  protected DataTableCell emptyCell() {
    return RawTextTableCell.of("");
  }
}

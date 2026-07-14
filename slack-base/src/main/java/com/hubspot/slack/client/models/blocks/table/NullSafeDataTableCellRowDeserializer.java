package com.hubspot.slack.client.models.blocks.table;

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

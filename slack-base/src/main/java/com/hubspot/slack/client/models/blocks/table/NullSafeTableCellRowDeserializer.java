package com.hubspot.slack.client.models.blocks.table;

public class NullSafeTableCellRowDeserializer
  extends AbstractNullSafeTableCellRowDeserializer<TableCell> {

  public NullSafeTableCellRowDeserializer() {
    super(TableCell.class);
  }

  @Override
  protected TableCell emptyCell() {
    return RawTextTableCell.of("");
  }
}

package com.hubspot.slack.client.models.blocks.table.deserializer;

import com.hubspot.slack.client.models.blocks.table.RawTextTableCell;
import com.hubspot.slack.client.models.blocks.table.TableCell;

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

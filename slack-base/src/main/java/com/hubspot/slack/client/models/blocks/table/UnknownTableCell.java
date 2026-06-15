package com.hubspot.slack.client.models.blocks.table;

public class UnknownTableCell implements DataTableCell {

  public static final String TYPE = "unknown";

  protected UnknownTableCell() {}

  @Override
  public String getType() {
    return TYPE;
  }
}

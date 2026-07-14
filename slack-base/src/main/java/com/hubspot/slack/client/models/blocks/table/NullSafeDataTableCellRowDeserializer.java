package com.hubspot.slack.client.models.blocks.table;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.common.collect.ImmutableList;
import java.io.IOException;

public class NullSafeDataTableCellRowDeserializer
  extends StdDeserializer<ImmutableList<DataTableCell>> {

  public NullSafeDataTableCellRowDeserializer() {
    super(ImmutableList.class);
  }

  @Override
  public ImmutableList<DataTableCell> deserialize(
    JsonParser p,
    DeserializationContext ctxt
  ) throws IOException {
    ImmutableList.Builder<DataTableCell> builder = ImmutableList.builder();
    while (p.nextToken() != JsonToken.END_ARRAY) {
      if (p.currentToken() == JsonToken.VALUE_NULL) {
        builder.add(RawTextTableCell.of(""));
      } else {
        builder.add(ctxt.readValue(p, DataTableCell.class));
      }
    }
    return builder.build();
  }
}

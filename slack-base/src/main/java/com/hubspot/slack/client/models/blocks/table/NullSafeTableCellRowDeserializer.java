package com.hubspot.slack.client.models.blocks.table;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.common.collect.ImmutableList;
import java.io.IOException;

public class NullSafeTableCellRowDeserializer
  extends StdDeserializer<ImmutableList<TableCell>> {

  public NullSafeTableCellRowDeserializer() {
    super(ImmutableList.class);
  }

  @Override
  public ImmutableList<TableCell> deserialize(JsonParser p, DeserializationContext ctxt)
    throws IOException {
    ImmutableList.Builder<TableCell> builder = ImmutableList.builder();
    while (p.nextToken() != JsonToken.END_ARRAY) {
      if (p.currentToken() == JsonToken.VALUE_NULL) {
        builder.add(RawTextTableCell.of(""));
      } else {
        builder.add(ctxt.readValue(p, TableCell.class));
      }
    }
    return builder.build();
  }
}

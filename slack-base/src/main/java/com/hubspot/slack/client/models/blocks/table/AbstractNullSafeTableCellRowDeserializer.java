package com.hubspot.slack.client.models.blocks.table;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.common.collect.ImmutableList;
import java.io.IOException;

abstract class AbstractNullSafeTableCellRowDeserializer<T extends TableCell>
  extends StdDeserializer<ImmutableList<T>> {

  private final Class<T> cellType;

  protected AbstractNullSafeTableCellRowDeserializer(Class<T> cellType) {
    super(Object.class);
    this.cellType = cellType;
  }

  protected abstract T emptyCell();

  @Override
  public ImmutableList<T> deserialize(JsonParser p, DeserializationContext ctxt)
    throws IOException {
    ImmutableList.Builder<T> builder = ImmutableList.builder();
    while (p.nextToken() != JsonToken.END_ARRAY) {
      if (p.currentToken() == JsonToken.VALUE_NULL) {
        builder.add(emptyCell());
      } else {
        builder.add(ctxt.readValue(p, cellType));
      }
    }
    return builder.build();
  }
}

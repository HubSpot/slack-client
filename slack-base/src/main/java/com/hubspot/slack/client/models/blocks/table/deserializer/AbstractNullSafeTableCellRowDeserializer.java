package com.hubspot.slack.client.models.blocks.table.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.common.collect.ImmutableList;
import com.hubspot.slack.client.models.blocks.table.TableCell;
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
  public ImmutableList<T> deserialize(JsonParser parser, DeserializationContext context)
    throws IOException {
    ImmutableList.Builder<T> builder = ImmutableList.builder();
    while (parser.nextToken() != JsonToken.END_ARRAY) {
      if (parser.currentToken() == JsonToken.VALUE_NULL) {
        builder.add(emptyCell());
      } else {
        builder.add(context.readValue(parser, cellType));
      }
    }
    return builder.build();
  }
}

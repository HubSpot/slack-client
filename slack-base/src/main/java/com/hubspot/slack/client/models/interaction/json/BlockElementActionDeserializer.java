package com.hubspot.slack.client.models.interaction.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hubspot.slack.client.models.blocks.elements.BlockElement;
import com.hubspot.slack.client.models.interaction.BlockElementAction;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class BlockElementActionDeserializer extends StdDeserializer<BlockElementAction> {

  private static final String BLOCK_ID_FIELD = "block_id";
  private static final String ACTION_ID_FIELD = "action_id";
  private static final String VALUE_FIELD = "value";
  private static final String SELECTED_DATE_FIELD = "selected_date";
  private static final String ACTION_TS_FIELD = "action_ts";

  protected BlockElementActionDeserializer() {
    super(BlockElementAction.class);
  }

  @Override
  public BlockElementAction deserialize(JsonParser p, DeserializationContext context)
    throws IOException {
    BlockElementAction.Builder builder = BlockElementAction.builder();
    ObjectCodec codec = p.getCodec();
    JsonNode node = codec.readTree(p);
    builder.setBlockId(node.get(BLOCK_ID_FIELD).asText());
    builder.setActionId(node.get(ACTION_ID_FIELD).asText());

    // select menu elements don't send a value field, they send a `selected_option` object that has a value field
    if (node.has("selected_option")) {
      builder.setSelectedValue(
        readOptionalString(node.get("selected_option"), VALUE_FIELD)
      );
    } else {
      builder.setSelectedValue(readOptionalString(node, VALUE_FIELD));
    }

    // datepickers don't have a value field, but they have a "selected_date" field with a LocalDate-style string
    if (node.has("selected_date")) {
      readOptionalString(node, SELECTED_DATE_FIELD)
        .map(LocalDate::parse)
        .ifPresent(builder::setSelectedDate);
    }

    builder.setActionTs(readOptionalString(node, ACTION_TS_FIELD));
    BlockElement element = codec.treeToValue(node, BlockElement.class);
    builder.setElement(element);
    return builder.build();
  }

  private Optional<String> readOptionalString(JsonNode node, String fieldName) {
    if (node.has(fieldName)) {
      return Optional.of(node.get(fieldName).asText());
    }
    return Optional.empty();
  }
}

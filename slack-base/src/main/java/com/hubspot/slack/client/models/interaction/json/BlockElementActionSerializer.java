package com.hubspot.slack.client.models.interaction.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.hubspot.slack.client.models.blocks.elements.BlockElement;
import com.hubspot.slack.client.models.interaction.BlockElementAction;
import java.io.IOException;
import java.util.Optional;

public class BlockElementActionSerializer extends StdSerializer<BlockElementAction> {

  private static final String BLOCK_ID_FIELD = "block_id";
  private static final String ACTION_ID_FIELD = "action_id";
  private static final String VALUE_FIELD = "value";
  private static final String ACTION_TS_FIELD = "action_ts";

  protected BlockElementActionSerializer() {
    super(BlockElementAction.class);
  }

  @Override
  public void serialize(
    BlockElementAction value,
    JsonGenerator gen,
    SerializerProvider provider
  ) throws IOException {
    gen.writeStartObject();
    writeBlockElement(value.getElement(), gen, provider);
    gen.writeStringField(BLOCK_ID_FIELD, value.getBlockId());
    gen.writeStringField(ACTION_ID_FIELD, value.getActionId());
    writeOptionalString(value.getSelectedValue(), gen, VALUE_FIELD);
    writeOptionalString(value.getActionTs(), gen, ACTION_TS_FIELD);
    gen.writeEndObject();
  }

  private void writeBlockElement(
    BlockElement element,
    JsonGenerator gen,
    SerializerProvider provider
  ) throws IOException {
    JavaType javaType = provider.constructType(element.getClass());
    BeanDescription beanDesc = provider.getConfig().introspect(javaType);
    JsonSerializer<Object> serializer =
      BeanSerializerFactory.instance.findBeanOrAddOnSerializer(
        provider,
        javaType,
        beanDesc,
        false
      );
    serializer.unwrappingSerializer(null).serialize(element, gen, provider);
  }

  private void writeOptionalString(
    Optional<String> value,
    JsonGenerator gen,
    String fieldName
  ) throws IOException {
    if (value.isPresent()) {
      gen.writeStringField(fieldName, value.get());
    }
  }
}

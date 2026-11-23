package com.hubspot.slack.client.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.hubspot.slack.client.models.Attachment;
import java.io.IOException;
import java.util.List;

// for compatibility with Slack, an attachment with "blocks" is written without the other legacy
// fields (empty lists, nulls), except for "color" and "fallback" when they are set
public class AttachmentSerializer extends StdSerializer<Attachment> {

  private static final String BLOCKS_FIELD_NAME = "blocks";
  private static final String COLOR_FIELD_NAME = "color";
  private static final String FALLBACK_FIELD_NAME = "fallback";

  public AttachmentSerializer() {
    this(Attachment.class);
  }

  public AttachmentSerializer(Class<Attachment> t) {
    super(t);
  }

  @Override
  public void serialize(
    Attachment attachment,
    JsonGenerator jgen,
    SerializerProvider provider
  ) throws IOException {
    if (attachment.getBlocks().isEmpty()) {
      serializeByDefault(attachment, jgen, provider);
    } else {
      jgen.writeStartObject();
      writeArray(jgen, attachment.getBlocks(), BLOCKS_FIELD_NAME);
      attachment
        .getColor()
        .ifPresent(color -> writeString(jgen, COLOR_FIELD_NAME, color));
      attachment
        .getFallback()
        .ifPresent(fallback -> writeString(jgen, FALLBACK_FIELD_NAME, fallback));
      jgen.writeEndObject();
    }
  }

  private void writeArray(JsonGenerator jgen, List<?> items, String arrayName)
    throws IOException {
    jgen.writeArrayFieldStart(arrayName);

    items.forEach(item -> {
      try {
        jgen.writeObject(item);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    jgen.writeEndArray();
  }

  private void writeString(JsonGenerator jgen, String fieldName, String value) {
    try {
      jgen.writeStringField(fieldName, value);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void serializeByDefault(
    Attachment attachment,
    JsonGenerator jgen,
    SerializerProvider provider
  ) throws IOException {
    JavaType javaType = provider.constructType(Attachment.class);
    BeanDescription beanDesc = provider.getConfig().introspect(javaType);
    JsonSerializer<Object> defaultSerializer =
      BeanSerializerFactory.instance.findBeanOrAddOnSerializer(
        provider,
        javaType,
        beanDesc,
        false
      );
    defaultSerializer.serialize(attachment, jgen, provider);
  }
}

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

// this custom serializer is used for compatibility with some Slack API features -
// if our attachment has a not empty "blocks" field, all other fields should not be present
public class AttachmentSerializer extends StdSerializer<Attachment> {

  private static final String BLOCKS_FIELD_NAME = "blocks";

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

  private void serializeByDefault(
    Attachment attachment,
    JsonGenerator jgen,
    SerializerProvider provider
  ) throws IOException {
    JavaType javaType = provider.constructType(Attachment.class);
    BeanDescription beanDesc = provider.getConfig().introspect(javaType);
    JsonSerializer<Object> defaultSerializer = BeanSerializerFactory.instance.findBeanSerializer(
      provider,
      javaType,
      beanDesc
    );
    defaultSerializer.serialize(attachment, jgen, provider);
  }
}

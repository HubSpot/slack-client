package com.hubspot.slack.client.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.hubspot.slack.client.methods.params.chat.ChatUpdateMessageParams;
import java.util.List;

/**
 * Swaps the default {@code attachments} / {@code blocks} property writers on
 * {@link ChatUpdateMessageParams} for {@link ForceEmitPropertyWriter}s driven by that type's
 * {@code shouldClearAttachments()} / {@code shouldClearBlocks()} flags. Every other property keeps
 * its default writer, so the only behavioral change is that an empty {@code attachments}/
 * {@code blocks} list is emitted as {@code []} (instead of being omitted) when the matching flag is
 * set — the signal Slack's {@code chat.update} needs to clear that field. With the flags at their
 * {@code false} default, serialization is unchanged.
 */
public class ChatUpdateMessageParamsSerializerModifier extends BeanSerializerModifier {

  private static final String ATTACHMENTS_PROPERTY = "attachments";
  private static final String BLOCKS_PROPERTY = "blocks";

  @Override
  public List<BeanPropertyWriter> changeProperties(
    SerializationConfig config,
    BeanDescription beanDesc,
    List<BeanPropertyWriter> beanProperties
  ) {
    if (!ChatUpdateMessageParams.class.isAssignableFrom(beanDesc.getBeanClass())) {
      return beanProperties;
    }

    for (int i = 0; i < beanProperties.size(); i++) {
      BeanPropertyWriter writer = beanProperties.get(i);
      switch (writer.getName()) {
        case ATTACHMENTS_PROPERTY:
          beanProperties.set(
            i,
            new ForceEmitPropertyWriter(
              writer,
              bean -> ChatUpdateMessageParams.class.cast(bean).shouldClearAttachments()
            )
          );
          break;
        case BLOCKS_PROPERTY:
          beanProperties.set(
            i,
            new ForceEmitPropertyWriter(
              writer,
              bean -> ChatUpdateMessageParams.class.cast(bean).shouldClearBlocks()
            )
          );
          break;
        default:
          break;
      }
    }

    return beanProperties;
  }
}

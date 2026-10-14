package com.hubspot.slack.client.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import java.io.Serializable;

/**
 * Wraps a single {@link BeanPropertyWriter}. When {@code forceEmit} tests {@code true} for the bean
 * being serialized, the property is written even if its value is empty — bypassing the type's
 * {@code @JsonInclude(NON_EMPTY)} suppression so the field appears as {@code []}. Otherwise it
 * defers to the wrapped writer, which omits an empty collection exactly as before.
 *
 * <p>Used by {@link ChatUpdateMessageParamsSerializerModifier} to let {@code chat.update} actively
 * clear {@code attachments}/{@code blocks}; every other property keeps its default writer.
 */
public class ForceEmitPropertyWriter extends BeanPropertyWriter {

  /**
   * Decides, per bean instance, whether the wrapped property should be force-emitted. Declared
   * {@link Serializable} because {@link BeanPropertyWriter} is itself serializable, so this field
   * must not be left as unrestorable (transient) state.
   */
  @FunctionalInterface
  public interface ForceEmitPredicate extends Serializable {
    boolean test(Object bean);
  }

  private static final long SERIAL_VERSION_UID = 1L;

  private final ForceEmitPredicate forceEmit;

  public ForceEmitPropertyWriter(BeanPropertyWriter base, ForceEmitPredicate forceEmit) {
    super(base);
    this.forceEmit = forceEmit;
  }

  @Override
  public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov)
    throws Exception {
    if (!forceEmit.test(bean)) {
      // Unchanged behavior: the wrapped writer applies NON_EMPTY and omits an empty collection.
      super.serializeAsField(bean, gen, prov);
      return;
    }

    Object value = get(bean);
    gen.writeFieldName(getName());
    if (value == null) {
      prov.defaultSerializeNull(gen);
      return;
    }
    JsonSerializer<Object> serializer = _serializer != null
      ? _serializer
      : prov.findValueSerializer(value.getClass(), this);
    serializer.serialize(value, gen, prov);
  }
}

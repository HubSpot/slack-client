package com.hubspot.slack.client.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.hubspot.slack.client.methods.params.chat.ChatUpdateMessageParams;
import java.io.IOException;
import java.util.List;

/**
 * Custom serializer for {@link ChatUpdateMessageParams} that reproduces the type's default
 * {@code @JsonInclude(NON_EMPTY)} behavior for every field, with one deliberate addition: the
 * {@code attachments} / {@code blocks} arrays can be force-emitted as an empty array {@code []}
 * when {@link ChatUpdateMessageParams#shouldClearAttachments()} /
 * {@link ChatUpdateMessageParams#shouldClearBlocks()} are set.
 *
 * <p>Slack's {@code chat.update} only clears a field when it is sent as {@code []}; an omitted
 * field is left unchanged. Because the params type is {@code NON_EMPTY}, an empty list is normally
 * omitted — which is what every partial-update caller relies on (so the message keeps its existing
 * attachments/blocks). The clear flags are the opt-in way to instead wipe one of those fields.
 *
 * <p>This is attached to the type via {@code @JsonSerialize(using = …)} rather than registered on a
 * specific {@code ObjectMapper}, so the clear behavior is honored by <em>any</em> mapper that
 * serializes these params (consumers often serialize request bodies with their own mapper, not
 * {@link ObjectMapperUtils#mapper()}). With both flags left at their {@code false} default this
 * serializer emits byte-for-byte the same JSON as the previous default bean serialization.
 */
public class ChatUpdateMessageParamsSerializer
  extends StdSerializer<ChatUpdateMessageParams> {

  private static final String CHANNEL_FIELD = "channel";
  private static final String TEXT_FIELD = "text";
  private static final String TS_FIELD = "ts";
  private static final String FILE_IDS_FIELD = "file_ids";
  private static final String AS_USER_FIELD = "as_user";
  private static final String LINK_NAMES_FIELD = "link_names";
  private static final String PARSE_FIELD = "parse";
  private static final String ATTACHMENTS_FIELD = "attachments";
  private static final String BLOCKS_FIELD = "blocks";

  public ChatUpdateMessageParamsSerializer() {
    super(ChatUpdateMessageParams.class);
  }

  @Override
  public void serialize(
    ChatUpdateMessageParams params,
    JsonGenerator gen,
    SerializerProvider provider
  ) throws IOException {
    gen.writeStartObject();

    // MAINTENANCE: this serializer writes every field by hand. If a field is added to
    // ChatUpdateMessageParamsIF (or MessageParams), add it here too — otherwise it will silently be
    // dropped from the wire with no compile error. itSerializesEveryPopulatedField guards this.

    // NON_EMPTY string fields: present on the wire only when non-empty (Optional text included:
    // orElse(null) -> absent and empty-string both omitted).
    writeNonEmptyString(gen, CHANNEL_FIELD, params.getChannelId());
    writeNonEmptyString(gen, TEXT_FIELD, params.getText().orElse(null));
    writeNonEmptyString(gen, TS_FIELD, params.getTs());

    if (!params.getFileIds().isEmpty()) {
      gen.writeArrayFieldStart(FILE_IDS_FIELD);
      for (String fileId : params.getFileIds()) {
        gen.writeString(fileId);
      }
      gen.writeEndArray();
    }

    // as_user carries a field-level @JsonInclude(NON_ABSENT): present only when set.
    if (params.getAsUser().isPresent()) {
      gen.writeBooleanField(AS_USER_FIELD, params.getAsUser().get());
    }

    // @Default fields that are never "empty" under NON_EMPTY, so always present today.
    gen.writeBooleanField(LINK_NAMES_FIELD, params.shouldLinkNames());
    writeNonEmptyString(gen, PARSE_FIELD, params.getParse());

    writeListOrClear(
      gen,
      provider,
      ATTACHMENTS_FIELD,
      params.getAttachments(),
      params.shouldClearAttachments()
    );
    writeListOrClear(
      gen,
      provider,
      BLOCKS_FIELD,
      params.getBlocks(),
      params.shouldClearBlocks()
    );

    gen.writeEndObject();
  }

  private static void writeNonEmptyString(
    JsonGenerator gen,
    String fieldName,
    String value
  ) throws IOException {
    if (value != null && !value.isEmpty()) {
      gen.writeStringField(fieldName, value);
    }
  }

  /**
   * Writes {@code fieldName} as a JSON array of the list's elements when the list is non-empty, or
   * as an empty array {@code []} when {@code forceClear} is set (the Slack "clear this field"
   * signal). When the list is empty and {@code forceClear} is {@code false} the field is omitted,
   * matching {@code NON_EMPTY}. Each element is serialized through {@code provider} (not
   * {@code gen.writeObject}, which requires the generator to carry an {@code ObjectCodec}), so
   * element-level custom serializers (e.g. {@link AttachmentSerializer}) and per-call config apply
   * under whatever mapper is in use.
   */
  private static void writeListOrClear(
    JsonGenerator gen,
    SerializerProvider provider,
    String fieldName,
    List<?> items,
    boolean forceClear
  ) throws IOException {
    if (items.isEmpty() && !forceClear) {
      return;
    }
    gen.writeArrayFieldStart(fieldName);
    for (Object item : items) {
      provider.defaultSerializeValue(item, gen);
    }
    gen.writeEndArray();
  }
}

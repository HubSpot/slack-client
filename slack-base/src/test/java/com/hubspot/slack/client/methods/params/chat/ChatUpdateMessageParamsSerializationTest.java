package com.hubspot.slack.client.methods.params.chat;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.Attachment;
import com.hubspot.slack.client.models.blocks.Divider;
import org.junit.Test;

public class ChatUpdateMessageParamsSerializationTest {

  private static final ObjectMapper OBJECT_MAPPER = ObjectMapperUtils.mapper();

  @Test
  public void itSerializesFileIdsAsJsonArray() {
    ChatUpdateMessageParams params = ChatUpdateMessageParams
      .builder()
      .setChannelId("C123")
      .setTs("1790587670.603089")
      .setText("testText")
      .addFileIds("F0C4SK2P1EZ", "F0C4SK0RGGM")
      .build();

    JsonNode json = OBJECT_MAPPER.valueToTree(params);

    assertThat(json.get("file_ids").isArray()).isTrue();
    assertThat(json.get("file_ids"))
      .extracting(JsonNode::asText)
      .containsExactly("F0C4SK2P1EZ", "F0C4SK0RGGM");
  }

  @Test
  public void itOmitsFileIdsWhenEmpty() {
    ChatUpdateMessageParams params = ChatUpdateMessageParams
      .builder()
      .setChannelId("C123")
      .setTs("1790587670.603089")
      .setText("testText")
      .build();

    JsonNode json = OBJECT_MAPPER.valueToTree(params);

    assertThat(json.has("file_ids")).isFalse();
  }

  @Test
  public void itOmitsEmptyAttachmentsWhenNotClearing() {
    ChatUpdateMessageParams params = ChatUpdateMessageParams
      .builder()
      .setChannelId("C123")
      .setTs("1790587670.603089")
      .setText("testText")
      .build();

    JsonNode json = OBJECT_MAPPER.valueToTree(params);

    assertThat(json.has("attachments")).isFalse();
  }

  @Test
  public void itEmitsEmptyAttachmentsArrayWhenClearing() {
    ChatUpdateMessageParams params = ChatUpdateMessageParams
      .builder()
      .setChannelId("C123")
      .setTs("1790587670.603089")
      .setText("testText")
      .setShouldClearAttachments(true)
      .build();

    JsonNode json = OBJECT_MAPPER.valueToTree(params);

    assertThat(json.get("attachments").isArray()).isTrue();
    assertThat(json.get("attachments")).isEmpty();
  }

  @Test
  public void itOmitsEmptyBlocksWhenNotClearing() {
    ChatUpdateMessageParams params = ChatUpdateMessageParams
      .builder()
      .setChannelId("C123")
      .setTs("1790587670.603089")
      .setText("testText")
      .build();

    JsonNode json = OBJECT_MAPPER.valueToTree(params);

    assertThat(json.has("blocks")).isFalse();
  }

  @Test
  public void itEmitsEmptyBlocksArrayWhenClearing() {
    ChatUpdateMessageParams params = ChatUpdateMessageParams
      .builder()
      .setChannelId("C123")
      .setTs("1790587670.603089")
      .setText("testText")
      .setShouldClearBlocks(true)
      .build();

    JsonNode json = OBJECT_MAPPER.valueToTree(params);

    assertThat(json.get("blocks").isArray()).isTrue();
    assertThat(json.get("blocks")).isEmpty();
  }

  @Test
  public void itDoesNotLeakTheClearFlagsIntoThePayload() {
    ChatUpdateMessageParams params = ChatUpdateMessageParams
      .builder()
      .setChannelId("C123")
      .setTs("1790587670.603089")
      .setText("testText")
      .setShouldClearAttachments(true)
      .setShouldClearBlocks(true)
      .build();

    JsonNode json = OBJECT_MAPPER.valueToTree(params);

    assertThat(json.has("should_clear_attachments")).isFalse();
    assertThat(json.has("should_clear_blocks")).isFalse();
  }

  @Test
  public void itLeavesNonEmptyAttachmentsUntouchedWhenClearing() {
    // Flag + a real attachment: the flag only forces emission of an EMPTY list, so a populated
    // list is written normally (the flag is effectively a no-op here).
    ChatUpdateMessageParams params = ChatUpdateMessageParams
      .builder()
      .setChannelId("C123")
      .setTs("1790587670.603089")
      .setText("testText")
      .addAttachments(Attachment.builder().setText("attachment").build())
      .setShouldClearAttachments(true)
      .build();

    JsonNode json = OBJECT_MAPPER.valueToTree(params);

    assertThat(json.get("attachments").isArray()).isTrue();
    assertThat(json.get("attachments")).hasSize(1);
  }

  /**
   * The modifier swaps only the attachments/blocks writers, so every other field must keep its
   * default writer and still serialize with the expected names and values.
   */
  @Test
  public void itSerializesEveryPopulatedField() {
    ChatUpdateMessageParams params = ChatUpdateMessageParams
      .builder()
      .setChannelId("C123")
      .setTs("1790587670.603089")
      .setText("testText")
      .addFileIds("F0C4SK2P1EZ")
      .setAsUser(true)
      .setShouldLinkNames(false)
      .setParse("full")
      .addAttachments(Attachment.builder().setText("attachment").build())
      .addBlocks(Divider.builder().build())
      .build();

    JsonNode json = OBJECT_MAPPER.valueToTree(params);

    assertThat(json.get("channel").asText()).isEqualTo("C123");
    assertThat(json.get("ts").asText()).isEqualTo("1790587670.603089");
    assertThat(json.get("text").asText()).isEqualTo("testText");
    assertThat(json.get("file_ids"))
      .extracting(JsonNode::asText)
      .containsExactly("F0C4SK2P1EZ");
    assertThat(json.get("as_user").asBoolean()).isTrue();
    assertThat(json.get("link_names").asBoolean()).isFalse();
    assertThat(json.get("parse").asText()).isEqualTo("full");
    assertThat(json.get("attachments").isArray()).isTrue();
    assertThat(json.get("attachments")).hasSize(1);
    assertThat(json.get("blocks").isArray()).isTrue();
    assertThat(json.get("blocks")).hasSize(1);
    assertThat(json.get("blocks").get(0).get("type").asText()).isEqualTo("divider");
  }

  @Test
  public void itLeavesNonEmptyBlocksUntouchedWhenClearing() {
    ChatUpdateMessageParams params = ChatUpdateMessageParams
      .builder()
      .setChannelId("C123")
      .setTs("1790587670.603089")
      .setText("testText")
      .addBlocks(Divider.builder().build())
      .setShouldClearBlocks(true)
      .build();

    JsonNode json = OBJECT_MAPPER.valueToTree(params);

    assertThat(json.get("blocks").isArray()).isTrue();
    assertThat(json.get("blocks")).hasSize(1);
  }

  @Test
  public void itClearsOnlyTheFlaggedField() {
    // Clearing attachments must not also emit an empty blocks array, and vice versa — the two
    // flags are independent.
    JsonNode clearingAttachments = OBJECT_MAPPER.valueToTree(
      ChatUpdateMessageParams
        .builder()
        .setChannelId("C123")
        .setTs("1790587670.603089")
        .setText("testText")
        .setShouldClearAttachments(true)
        .build()
    );
    assertThat(clearingAttachments.get("attachments").isArray()).isTrue();
    assertThat(clearingAttachments.get("attachments")).isEmpty();
    assertThat(clearingAttachments.has("blocks")).isFalse();

    JsonNode clearingBlocks = OBJECT_MAPPER.valueToTree(
      ChatUpdateMessageParams
        .builder()
        .setChannelId("C123")
        .setTs("1790587670.603089")
        .setText("testText")
        .setShouldClearBlocks(true)
        .build()
    );
    assertThat(clearingBlocks.get("blocks").isArray()).isTrue();
    assertThat(clearingBlocks.get("blocks")).isEmpty();
    assertThat(clearingBlocks.has("attachments")).isFalse();
  }

  @Test
  public void itDefaultsBothClearFlagsToFalse() {
    ChatUpdateMessageParams params = ChatUpdateMessageParams
      .builder()
      .setChannelId("C123")
      .setTs("1790587670.603089")
      .setText("testText")
      .build();

    assertThat(params.shouldClearAttachments()).isFalse();
    assertThat(params.shouldClearBlocks()).isFalse();
  }

  /**
   * The custom serializer is attached only to {@link ChatUpdateMessageParams} via
   * {@code @JsonSerialize}, so a different {@link MessageParams} type is unaffected and keeps its
   * own serialization rules.
   */
  @Test
  public void itDoesNotAffectOtherMessageParamTypes() {
    ChatPostMessageParams postParams = ChatPostMessageParams
      .builder()
      .setChannelId("C123")
      .setText("testText")
      .build();

    JsonNode json = OBJECT_MAPPER.valueToTree(postParams);

    assertThat(json.get("channel").asText()).isEqualTo("C123");
    assertThat(json.has("should_clear_attachments")).isFalse();
    assertThat(json.has("should_clear_blocks")).isFalse();
  }

  /**
   * The whole reason for {@code @JsonSerialize} on the type (rather than a mapper-level modifier):
   * consumers serialize request bodies with their own {@link ObjectMapper}, not
   * {@link ObjectMapperUtils#mapper()}. A foreign mapper with no slack-specific configuration must
   * still honor the clear flags and emit {@code []}, otherwise the field never gets cleared and the
   * message double-renders. This mirrors how slack-integration serializes (its own mapper).
   */
  @Test
  public void itHonorsClearFlagsUnderAForeignObjectMapper() {
    ObjectMapper foreignMapper = new ObjectMapper()
      .registerModule(new Jdk8Module())
      .registerModule(new GuavaModule());

    JsonNode json = foreignMapper.valueToTree(
      ChatUpdateMessageParams
        .builder()
        .setChannelId("C123")
        .setTs("1790587670.603089")
        .setText("testText")
        .setShouldClearBlocks(true)
        .build()
    );

    assertThat(json.get("blocks").isArray()).isTrue();
    assertThat(json.get("blocks")).isEmpty();
    assertThat(json.has("attachments")).isFalse();
  }

  @Test
  public void itHonorsClearAttachmentsUnderAForeignObjectMapper() {
    ObjectMapper foreignMapper = new ObjectMapper()
      .registerModule(new Jdk8Module())
      .registerModule(new GuavaModule());

    JsonNode json = foreignMapper.valueToTree(
      ChatUpdateMessageParams
        .builder()
        .setChannelId("C123")
        .setTs("1790587670.603089")
        .setText("testText")
        .setShouldClearAttachments(true)
        .build()
    );

    assertThat(json.get("attachments").isArray()).isTrue();
    assertThat(json.get("attachments")).isEmpty();
    assertThat(json.has("blocks")).isFalse();
  }

  /**
   * Mirrors {@code NON_EMPTY} for the Optional {@code text} field: a present-but-empty string is
   * omitted, not written as {@code ""}. A block is included so the {@code @Check} (text or
   * attachments or blocks required) still passes.
   */
  @Test
  public void itOmitsEmptyTextString() {
    ChatUpdateMessageParams params = ChatUpdateMessageParams
      .builder()
      .setChannelId("C123")
      .setTs("1790587670.603089")
      .setText("")
      .addBlocks(Divider.builder().build())
      .build();

    JsonNode json = OBJECT_MAPPER.valueToTree(params);

    assertThat(json.has("text")).isFalse();
    assertThat(json.get("blocks")).hasSize(1);
  }
}

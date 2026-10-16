package com.hubspot.slack.client.methods.params.chat;

import static com.hubspot.slack.client.TestConstants.TEST_ATTACHMENTS_TEXT;
import static com.hubspot.slack.client.TestConstants.TEST_CHANNEL_ID;
import static com.hubspot.slack.client.TestConstants.TEST_FILE_ID;
import static com.hubspot.slack.client.TestConstants.TEST_MESSAGE_TEXT;
import static com.hubspot.slack.client.TestConstants.TEST_MESSAGE_TS;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.Attachment;
import com.hubspot.slack.client.models.blocks.Divider;
import org.junit.Test;

/**
 * Stronger drift guard than {@code itSerializesEveryPopulatedField}: instead of asserting a fixed
 * list of fields, it derives the set of serializable properties from the type via Jackson
 * introspection ({@code @JsonIgnore} properties — the shouldClear* flags — are excluded) and
 * asserts the hand-written {@code ChatUpdateMessageParamsSerializer} emits exactly that many fields
 * for a fully-populated instance. Add a getter to ChatUpdateMessageParamsIF (or MessageParams)
 * without updating the serializer and this fails, where a by-hand test would not.
 */
public class ChatUpdateMessageParamsSerializerDriftReflectionTest {

  private static final ObjectMapper OBJECT_MAPPER = ObjectMapperUtils.mapper();

  @Test
  public void itSerializesEveryNonIgnoredProperty() {
    JavaType type = OBJECT_MAPPER.constructType(ChatUpdateMessageParams.class);
    BeanDescription description = OBJECT_MAPPER.getSerializationConfig().introspect(type);
    int declaredSerializableProperties = description.findProperties().size();

    ChatUpdateMessageParams fullyPopulated = ChatUpdateMessageParams
      .builder()
      .setChannelId(TEST_CHANNEL_ID)
      .setTs(TEST_MESSAGE_TS)
      .setText(TEST_MESSAGE_TEXT)
      .addFileIds(TEST_FILE_ID)
      .setAsUser(true)
      .setShouldLinkNames(false)
      .setParse("full")
      .addAttachments(Attachment.builder().setText(TEST_ATTACHMENTS_TEXT).build())
      .addBlocks(Divider.builder().build())
      .build();

    JsonNode json = OBJECT_MAPPER.valueToTree(fullyPopulated);

    assertThat(json.size())
      .as(
        "ChatUpdateMessageParamsSerializer must emit one field per non-@JsonIgnore property; " +
        "a mismatch means a property was added to the type but not to the serializer"
      )
      .isEqualTo(declaredSerializableProperties);
  }
}

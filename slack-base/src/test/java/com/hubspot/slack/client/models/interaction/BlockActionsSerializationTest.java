package com.hubspot.slack.client.models.interaction;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hubspot.slack.client.SerializationTestBase;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.SlackChannel;
import java.io.IOException;
import org.junit.Test;

public class BlockActionsSerializationTest extends SerializationTestBase {

  private static final ObjectMapper MAPPER = ObjectMapperUtils.mapper();

  @Test
  public void testBlockSerialization() throws IOException {
    testSerialization("block_actions.json", BlockActions.class);
  }

  @Test
  public void testBlockSerializationWithoutState() throws IOException {
    testSerialization("block_actions_without_state.json", BlockActions.class);
  }

  @Test
  public void testBlockSerializationWithoutContainer() throws IOException {
    testSerialization("block_actions_without_container.json", BlockActions.class);
  }

  @Test
  public void testBlockSerializationWithMessageContainer() throws IOException {
    testSerialization("block_actions_with_message_container.json", BlockActions.class);
  }

  @Test
  public void testBlockSerializationWithViewContainer() throws IOException {
    testSerialization("block_actions_with_view_container.json", BlockActions.class);
  }

  @Test
  public void testBlockDeserializationWithChannel() throws IOException {
    ObjectNode json = blockActionsPayload();
    json.putObject("channel").put("id", "C123").put("name", "general");

    BlockActions actions = MAPPER.treeToValue(json, BlockActions.class);

    assertThat(actions.getChannel()).isNotNull();
    assertThat(actions.getChannel().getId()).isEqualTo("C123");
    assertThat(actions.getChannel().getName()).isEqualTo("general");
    JsonNode serialized = MAPPER.valueToTree(actions);
    assertThat(serialized.path("channel").path("id").asText()).isEqualTo("C123");
    assertThat(serialized.path("channel").path("name").asText()).isEqualTo("general");
    assertThat(MAPPER.treeToValue(serialized, BlockActions.class)).isEqualTo(actions);
  }

  @Test
  public void testCallbackDeserializationWithChannel() throws IOException {
    ObjectNode json = blockActionsPayload();
    json.putObject("channel").put("id", "C123").put("name", "general");

    SlackInteractiveCallback callback = MAPPER.treeToValue(
      json,
      SlackInteractiveCallback.class
    );

    assertThat(callback).isInstanceOf(BlockActions.class);
    assertThat(callback.getChannel()).isNotNull();
    assertThat(callback.getChannel().getId()).isEqualTo("C123");
    assertThat(callback.getChannel().getName()).isEqualTo("general");
    JsonNode serialized = MAPPER.valueToTree(callback);
    assertThat(serialized.path("channel").path("id").asText()).isEqualTo("C123");
    assertThat(serialized.path("channel").path("name").asText()).isEqualTo("general");
    assertThat(MAPPER.treeToValue(serialized, SlackInteractiveCallback.class))
      .isEqualTo(callback);
  }

  @Test
  public void testBlockDeserializationWithoutChannel() throws IOException {
    ObjectNode json = blockActionsPayload();
    assertThat(json.path("container").path("channel_id").asText()).isNotEmpty();

    BlockActions actions = MAPPER.treeToValue(json, BlockActions.class);

    assertThat(actions.getChannel()).isNull();
    JsonNode serialized = MAPPER.valueToTree(actions);
    assertThat(serialized.has("channel")).isFalse();
    assertThat(serialized).isEqualTo(json);
  }

  @Test
  public void testBlockDeserializationWithNullChannel() throws IOException {
    ObjectNode json = blockActionsPayload();
    json.putNull("channel");

    BlockActions actions = MAPPER.treeToValue(json, BlockActions.class);

    assertThat(actions.getChannel()).isNull();
    JsonNode serialized = MAPPER.valueToTree(actions);
    assertThat(serialized.has("channel")).isFalse();
    assertThat(serialized).isEqualTo(blockActionsPayload());
  }

  @Test
  public void testBlockBuilderCopiesChannel() throws IOException {
    BlockActions original = MAPPER.treeToValue(blockActionsPayload(), BlockActions.class);
    SlackChannel channel = SlackChannel
      .builder()
      .setId("C123")
      .setName("general")
      .build();

    BlockActions actions = BlockActions
      .builder()
      .from(original)
      .setChannel(channel)
      .build();
    BlockActions copy = BlockActions.builder().from(actions).build();

    assertThat(actions.getChannel()).isEqualTo(channel);
    assertThat(copy.getChannel()).isEqualTo(channel);
    assertThat(copy).isEqualTo(actions);
    assertThat(original.getChannel()).isNull();
  }

  private static ObjectNode blockActionsPayload() throws IOException {
    return (ObjectNode) MAPPER.readTree(
      JsonLoader.loadJsonFromFile("block_actions_with_message_container.json")
    );
  }
}

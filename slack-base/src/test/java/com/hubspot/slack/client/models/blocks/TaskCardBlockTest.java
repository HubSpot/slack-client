package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import java.io.IOException;
import org.junit.Test;

public class TaskCardBlockTest {

  @Test
  public void itDeserializesFromJson() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("task_card_block.json");
    Block block = ObjectMapperUtils.mapper().readValue(rawJson, Block.class);
    assertThat(block).isInstanceOf(TaskCardBlock.class);
    TaskCardBlock taskCardBlock = ObjectMapperUtils
      .mapper()
      .readValue(rawJson, TaskCardBlock.class);
    assertThat(taskCardBlock.getTaskId())
      .isEqualTo("fefba48f-80d8-4a4c-923f-e0f260d0fd76");
    assertThat(taskCardBlock.getTitle())
      .isEqualTo("Demonstrating Task Card Block Features...");
    assertThat(taskCardBlock.getStatus()).contains(TaskCardBlockStatus.IN_PROGRESS);
  }

  @Test
  public void itSerializesAndDeserializes() throws IOException {
    TaskCardBlock original = TaskCardBlock
      .builder()
      .setTaskId("abc-123")
      .setTitle("Test Task")
      .setStatus(TaskCardBlockStatus.PENDING)
      .build();
    ObjectMapper mapper = ObjectMapperUtils.mapper();
    String serialized = mapper.writeValueAsString(original);
    TaskCardBlock deserialized = mapper.readValue(serialized, TaskCardBlock.class);
    assertThat(deserialized).isEqualTo(original);
  }
}

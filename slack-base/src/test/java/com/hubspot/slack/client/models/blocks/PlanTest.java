package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import java.io.IOException;
import org.junit.Test;

public class PlanTest {

  private static TaskCardBlock task(String taskId, String title) {
    return TaskCardBlock.builder().setTaskId(taskId).setTitle(title).build();
  }

  @Test
  public void itSerializesAndDeserializes() throws IOException {
    Plan original = Plan
      .builder()
      .setTitle("My plan")
      .addTasks(task("t1", "First task"))
      .addTasks(task("t2", "Second task"))
      .build();
    ObjectMapper mapper = ObjectMapperUtils.mapper();
    String serialized = mapper.writeValueAsString(original);

    assertThat(mapper.readValue(serialized, Block.class)).isInstanceOf(Plan.class);
    assertThat(mapper.readValue(serialized, Plan.class)).isEqualTo(original);
  }

  @Test
  public void itRejectsEmptyPlan() {
    assertThatThrownBy(() -> Plan.builder().setTitle("My plan").build())
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("between 1 and 50");
  }

  @Test
  public void itRejectsDuplicateTaskIds() {
    assertThatThrownBy(() ->
        Plan
          .builder()
          .setTitle("My plan")
          .addTasks(task("dup", "First"))
          .addTasks(task("dup", "Second"))
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("task_id");
  }
}

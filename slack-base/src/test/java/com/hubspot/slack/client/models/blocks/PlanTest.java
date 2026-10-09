package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import java.io.IOException;
import org.junit.Test;

public class PlanTest {

  private static final ObjectMapper MAPPER = ObjectMapperUtils.mapper();

  private static PlanTask task(String taskId, String title) {
    return PlanTask.builder().setTaskId(taskId).setTitle(title).build();
  }

  @Test
  public void itSerializesAndDeserializes() throws IOException {
    Plan original = Plan
      .builder()
      .setTitle("My plan")
      .addTasks(task("t1", "First task"))
      .addTasks(task("t2", "Second task"))
      .build();
    String serialized = MAPPER.writeValueAsString(original);

    assertThat(MAPPER.readValue(serialized, Block.class)).isInstanceOf(Plan.class);
    assertThat(MAPPER.readValue(serialized, Plan.class)).isEqualTo(original);
  }

  @Test
  public void itDoesNotSerializeTaskTypeField() throws IOException {
    Plan plan = Plan.builder().setTitle("My plan").addTasks(task("t1", "A")).build();
    // Per Slack's plan docs, task objects carry no "type" field.
    assertThat(MAPPER.writeValueAsString(plan)).doesNotContain("task_card");
  }

  @Test
  public void itDeserializesTasksWithoutTypeField() throws IOException {
    String json =
      "{\"type\":\"plan\",\"title\":\"My plan\",\"tasks\":[" +
      "{\"task_id\":\"t1\",\"title\":\"First task\",\"status\":\"in_progress\"}]}";
    Plan plan = MAPPER.readValue(json, Plan.class);
    assertThat(plan.getTasks()).hasSize(1);
    assertThat(plan.getTasks().get(0).getTaskId()).isEqualTo("t1");
    assertThat(plan.getTasks().get(0).getStatus())
      .contains(TaskCardBlockStatus.IN_PROGRESS);
  }

  @Test
  public void itToleratesInboundTaskTypeField() throws IOException {
    // A task that includes "type":"task_card" must still parse (the field is ignored).
    String json =
      "{\"type\":\"plan\",\"title\":\"My plan\",\"tasks\":[" +
      "{\"type\":\"task_card\",\"task_id\":\"t1\",\"title\":\"First task\"}]}";
    Plan plan = MAPPER.readValue(json, Plan.class);
    assertThat(plan.getTasks().get(0).getTaskId()).isEqualTo("t1");
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

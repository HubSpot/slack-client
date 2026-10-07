package com.hubspot.slack.client.models.blocks.elements;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;
import com.hubspot.slack.client.models.blocks.objects.TriggerObject;
import com.hubspot.slack.client.models.blocks.objects.WorkflowObject;
import java.io.IOException;
import org.junit.Test;

public class WorkflowButtonTest {

  private static final WorkflowObject WORKFLOW = WorkflowObject.of(
    TriggerObject.of("https://slack.com/shortcuts/Ft0000000000/abc123")
  );

  private static WorkflowButton.Builder validButton() {
    return WorkflowButton
      .builder()
      .setText(Text.of(TextType.PLAIN_TEXT, "Run"))
      .setWorkflow(WORKFLOW)
      .setActionId("wb_1");
  }

  private static String repeat(int length) {
    return new String(new char[length]).replace('\0', 'a');
  }

  @Test
  public void itSerializesAndDeserializes() throws IOException {
    WorkflowButton original = validButton()
      .setStyle("primary")
      .setAccessibilityLabel("Run the workflow")
      .build();
    ObjectMapper mapper = ObjectMapperUtils.mapper();
    String serialized = mapper.writeValueAsString(original);

    assertThat(mapper.readValue(serialized, BlockElement.class))
      .isInstanceOf(WorkflowButton.class);
    assertThat(mapper.readValue(serialized, WorkflowButton.class)).isEqualTo(original);
  }

  @Test
  public void itRejectsNonPlainTextText() {
    assertThatThrownBy(() ->
        WorkflowButton
          .builder()
          .setText(Text.of(TextType.MARKDOWN, "Run"))
          .setWorkflow(WORKFLOW)
          .setActionId("wb_1")
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("plain_text");
  }

  @Test
  public void itRejectsTextOver75Chars() {
    assertThatThrownBy(() ->
        validButton().setText(Text.of(TextType.PLAIN_TEXT, repeat(76))).build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("text cannot exceed 75");
  }

  @Test
  public void itRejectsActionIdOver255Chars() {
    assertThatThrownBy(() -> validButton().setActionId(repeat(256)).build())
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("action_id cannot exceed 255");
  }

  @Test
  public void itRejectsAccessibilityLabelOver75Chars() {
    assertThatThrownBy(() -> validButton().setAccessibilityLabel(repeat(76)).build())
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("accessibility_label cannot exceed 75");
  }
}

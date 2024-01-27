package com.hubspot.slack.client.models.interaction;

import com.hubspot.slack.client.SerializationTestBase;
import java.io.IOException;
import org.junit.Test;

public class WorkflowsSerializationTest extends SerializationTestBase {

  @Test
  public void workflowStepEdit() throws IOException {
    testSerializationPretty("workflow_step_edit.json", WorkflowStepEdit.class);
  }
}

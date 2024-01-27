package com.hubspot.slack.client.models.workflows;

import com.hubspot.slack.client.SerializationTestBase;
import java.io.IOException;
import org.junit.Test;

public class WorkflowStepSerializationTest extends SerializationTestBase {

  @Test
  public void testSerialization() throws IOException {
    testSerialization("workflow_step.json", WorkflowStep.class);
  }

}

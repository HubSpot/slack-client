package com.hubspot.slack.client.models.views;

import com.hubspot.slack.client.SerializationTestBase;
import com.hubspot.slack.client.models.interaction.ViewSubmission;
import java.io.IOException;
import org.junit.Test;

public class ViewSubmissionSerializationTest extends SerializationTestBase {

  @Test
  public void testViewPayloadSerialization() throws IOException {
    testSerialization("view_submission.json", ViewSubmission.class);
  }
}

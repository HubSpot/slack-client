package com.hubspot.slack.client.models.interaction;

import com.hubspot.slack.client.SerializationTestBase;
import java.io.IOException;
import org.junit.Test;

public class ViewResponseActionSerializationTest extends SerializationTestBase {

  @Test
  public void testViewResponseActionSerialization() throws IOException {
    testSerialization("view_response_action.json", ViewResponseAction.class);
  }
}

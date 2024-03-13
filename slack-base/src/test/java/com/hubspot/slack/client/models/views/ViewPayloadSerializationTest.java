package com.hubspot.slack.client.models.views;

import com.hubspot.slack.client.SerializationTestBase;
import java.io.IOException;
import org.junit.Test;

public class ViewPayloadSerializationTest extends SerializationTestBase {

  @Test
  public void testViewPayloadSerialization() throws IOException {
    testSerialization("view_payloads.json", ViewPayloadJsonBase[].class);
  }
}

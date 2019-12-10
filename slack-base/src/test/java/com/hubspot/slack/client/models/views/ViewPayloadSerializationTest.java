package com.hubspot.slack.client.models.views;

import java.io.IOException;

import org.junit.Test;

import com.hubspot.slack.client.SerializationTestBase;

public class ViewPayloadSerializationTest extends SerializationTestBase {

  @Test
  public void testViewPayloadSerialization() throws IOException {
    testSerialization("view_payloads.json", ViewPayloadJsonBase[].class);
  }
}

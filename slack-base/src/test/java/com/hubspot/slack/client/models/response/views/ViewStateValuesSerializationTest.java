package com.hubspot.slack.client.models.response.views;

import java.io.IOException;

import org.junit.Test;

import com.hubspot.slack.client.SerializationTestBase;

public class ViewStateValuesSerializationTest extends SerializationTestBase {

  @Test
  public void testViewPayloadSerialization() throws IOException {
    testSerialization("view_state_values.json", StateBlock.class);
  }
}

package com.hubspot.slack.client.models.blocks;

import java.io.IOException;

import com.hubspot.slack.client.SerializationTestBase;

public class BlockSerializationTest extends SerializationTestBase {

  public void testBlockSerialization() throws IOException {
    testSerialization("blocks.json", Block[].class);
  }
}

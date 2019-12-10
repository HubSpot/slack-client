package com.hubspot.slack.client.models.blocks.elements;

import java.io.IOException;

import org.junit.Test;

import com.hubspot.slack.client.SerializationTestBase;

public class BlockElementSerializationTest extends SerializationTestBase {

  @Test
  public void testBlockSerialization() throws IOException {
    testSerialization("block_elements.json", BlockElement[].class);
  }
}

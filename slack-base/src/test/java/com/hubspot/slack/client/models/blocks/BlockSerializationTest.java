package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;

import com.hubspot.slack.client.SerializationTestBase;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import java.io.IOException;
import org.junit.Test;

public class BlockSerializationTest extends SerializationTestBase {

  @Test
  public void testBlockSerialization() throws IOException {
    testSerialization("blocks.json", Block[].class);
  }

  @Test
  public void testUnknownBlockSerialization() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("unknown_blocks.json");
    Block[] blocks = ObjectMapperUtils.mapper().readValue(rawJson, Block[].class);
    assertThat(blocks.length).isEqualTo(1);
    assertThat(blocks[0]).isInstanceOf(UnknownBlock.class);
  }
}

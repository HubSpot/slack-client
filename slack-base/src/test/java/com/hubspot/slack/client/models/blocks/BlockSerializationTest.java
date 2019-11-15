package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;

public class BlockSerializationTest {

  private ObjectMapper objectMapper;

  @Before
  public void setup() {
    objectMapper = ObjectMapperUtils.mapper();
  }

  @Test
  public void testBlockSerialization() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("blocks.json");
    Block[] blocks = objectMapper.readValue(rawJson, Block[].class);
    assertThat(blocks).hasSize(9);
    String generatedJson = objectMapper.writeValueAsString(blocks);
    assertThat(objectMapper.readTree(rawJson)).isEqualTo(objectMapper.readTree(generatedJson));
  }
}

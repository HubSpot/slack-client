package com.hubspot.slack.client.models.blocks.elements;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;

public class BlockElementSerializationTest {

  private ObjectMapper objectMapper;

  @Before
  public void setup() {
    objectMapper = ObjectMapperUtils.mapper();
  }

  @Test
  public void testBlockSerialization() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("block_elements.json");
    BlockElement[] blockElements = objectMapper.readValue(rawJson, BlockElement[].class);
//    assertThat(blockElements).hasSize(9);
    String generatedJson = objectMapper.writeValueAsString(blockElements);
    assertThat(objectMapper.readTree(rawJson)).isEqualTo(objectMapper.readTree(generatedJson));
  }
}

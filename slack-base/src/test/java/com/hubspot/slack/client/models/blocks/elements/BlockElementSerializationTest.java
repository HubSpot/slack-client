package com.hubspot.slack.client.models.blocks.elements;

import static org.assertj.core.api.Assertions.assertThat;

import com.hubspot.slack.client.SerializationTestBase;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.blocks.Section;
import java.io.IOException;
import org.junit.Test;

public class BlockElementSerializationTest extends SerializationTestBase {

  @Test
  public void testBlockSerialization() throws IOException {
    testSerialization("block_elements.json", BlockElement[].class);
  }

  @Test
  public void testUnknownBlockSerialization() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("unknown_block_element.json");
    Section section = ObjectMapperUtils.mapper().readValue(rawJson, Section.class);
    assertThat(section.getAccessory().get()).isInstanceOf(UnknownBlockElement.class);
  }
}

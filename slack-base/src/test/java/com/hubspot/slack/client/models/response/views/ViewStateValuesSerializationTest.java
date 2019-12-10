package com.hubspot.slack.client.models.response.views;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;

public class ViewStateValuesSerializationTest {

  private ObjectMapper objectMapper;

  @Before
  public void setup() {
    objectMapper = ObjectMapperUtils.mapper();
  }

  @Test
  public void testViewPayloadSerialization() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("view_state_values.json");
    StateBlock viewPayloads = objectMapper.readValue(rawJson, StateBlock.class);
    String generatedJson = objectMapper.writeValueAsString(viewPayloads);
    assertThat(objectMapper.readTree(rawJson)).isEqualTo(objectMapper.readTree(generatedJson));
  }
}

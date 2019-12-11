package com.hubspot.slack.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;

public class SerializationTestBase {

  private ObjectMapper objectMapper = ObjectMapperUtils.mapper();

  protected <T> void testSerialization(String filename, Class<T> jsonClass) throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(filename);
    T jsonObject = objectMapper.readValue(rawJson, jsonClass);
    String generatedJson = objectMapper.writeValueAsString(jsonObject);
    assertThat(objectMapper.readTree(rawJson)).isEqualTo(objectMapper.readTree(generatedJson));
  }

}

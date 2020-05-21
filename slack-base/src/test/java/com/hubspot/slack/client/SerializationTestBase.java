package com.hubspot.slack.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import java.io.IOException;

public class SerializationTestBase {
  private static final ObjectMapper OBJECT_MAPPER = ObjectMapperUtils.mapper();

  protected <T> void testSerialization(String filename, Class<T> jsonClass)
    throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(filename);
    T jsonObject = OBJECT_MAPPER.readValue(rawJson, jsonClass);
    String generatedJson = OBJECT_MAPPER.writeValueAsString(jsonObject);
    assertThat(OBJECT_MAPPER.readTree(rawJson))
      .isEqualTo(OBJECT_MAPPER.readTree(generatedJson));
  }
}

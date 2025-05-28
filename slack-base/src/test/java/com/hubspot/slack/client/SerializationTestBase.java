package com.hubspot.slack.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import java.io.IOException;
import java.util.Map;

public class SerializationTestBase {

  private static final ObjectMapper OBJECT_MAPPER = ObjectMapperUtils.mapper();

  protected <T> void testSerialization(String filename, Class<T> jsonClass)
    throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(filename);
    T jsonObject = OBJECT_MAPPER.readValue(rawJson, jsonClass);
    String generatedJson = OBJECT_MAPPER.writeValueAsString(jsonObject);

    Map<String, Object> actual = OBJECT_MAPPER.readValue(
      rawJson,
      new TypeReference<Map<String, Object>>() {}
    );
    Map<String, Object> expected = OBJECT_MAPPER.readValue(
      generatedJson,
      new TypeReference<Map<String, Object>>() {}
    );

    assertThat(actual).isEqualTo(expected);
  }
}

package com.hubspot.slack.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import java.io.IOException;

public class SerializationTestBase {
  private static final ObjectMapper OBJECT_MAPPER = ObjectMapperUtils.mapper();
  private static final ObjectMapper PRETTY_MAPPER = ObjectMapperUtils.mapper();
  static {
    PRETTY_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
    PRETTY_MAPPER.configure(
        SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true
    );
    PRETTY_MAPPER.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
  }

  protected <T> void testSerializationPretty(String filename, Class<T> jsonClass)
    throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(filename);
    T jsonObject = PRETTY_MAPPER.readValue(rawJson, jsonClass);
    String generatedJson = PRETTY_MAPPER.writeValueAsString(jsonObject);
    assertThat(PRETTY_MAPPER.writeValueAsString(PRETTY_MAPPER.readTree(rawJson)))
      .isEqualTo(generatedJson);
  }

  protected <T> void testSerialization(String filename, Class<T> jsonClass)
      throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(filename);
    T jsonObject = OBJECT_MAPPER.readValue(rawJson, jsonClass);
    String generatedJson = OBJECT_MAPPER.writeValueAsString(jsonObject);
    assertThat(OBJECT_MAPPER.readTree(rawJson))
        .isEqualTo(OBJECT_MAPPER.readTree(generatedJson));
  }
}

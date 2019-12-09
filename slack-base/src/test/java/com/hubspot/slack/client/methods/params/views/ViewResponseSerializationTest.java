package com.hubspot.slack.client.methods.params.views;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.response.views.ModalViewCommandResponse;
import com.hubspot.slack.client.models.response.views.ModalViewResponse;

public class ViewResponseSerializationTest {
  private ObjectMapper objectMapper;

  @Before
  public void setup() {
    objectMapper = ObjectMapperUtils.mapper();
  }

  @Test
  public void testOpenViewSerialization() throws IOException {
    testSerialization("open_view_response.json", ModalViewCommandResponse.class);
  }

  @Test
  public void testUpdateViewSerialization() throws IOException {
    testSerialization("update_view_response.json", ModalViewCommandResponse.class);
  }

  private <T extends Object> void testSerialization(String filename, Class<T> responseClass) throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(filename);
    T response = objectMapper.readValue(rawJson, responseClass);
    String generatedJson = objectMapper.writeValueAsString(response);
    assertThat(objectMapper.readTree(rawJson)).isEqualTo(objectMapper.readTree(generatedJson));
  }

}

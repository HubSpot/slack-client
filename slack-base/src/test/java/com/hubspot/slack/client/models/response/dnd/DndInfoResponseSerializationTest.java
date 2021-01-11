package com.hubspot.slack.client.models.response.dnd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.SerializationTestBase;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class DndInfoResponseSerializationTest extends SerializationTestBase {
  private static final ObjectMapper OBJECT_MAPPER = ObjectMapperUtils.mapper();

  @Test
  public void testSerialization() throws IOException {
    testSerialization("dnd_response_1.json", DndInfoResponse.class);
    testSerialization("dnd_response_2.json", DndInfoResponse.class);
    testSerialization("dnd_response_3.json", DndInfoResponse.class);
  }

  @Test
  public void testDeserializationNoDndEstablishedYet() throws IOException {
    // special case: if a user has never configured their DND, the timestamps come back as "1" but we should treat as effectively null/empty
    String rawJson = JsonLoader.loadJsonFromFile("dnd_response_4.json");
    DndInfoResponse dndInfoResponse = OBJECT_MAPPER.readValue(rawJson, DndInfoResponse.class);
    assertThat(dndInfoResponse.getNextDndStart().isPresent()).isFalse();
    assertThat(dndInfoResponse.getNextDndEnd().isPresent()).isFalse();

  }
}

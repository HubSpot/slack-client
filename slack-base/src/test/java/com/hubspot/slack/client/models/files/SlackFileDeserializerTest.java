package com.hubspot.slack.client.models.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import java.io.IOException;
import org.junit.Test;

public class SlackFileDeserializerTest {

  @Test
  public void shouldDeserializeAccessDeniedFile() throws IOException {
    SlackFile file = fetchAndDeserializeSlackFile("file_with_access_denied.json");
    assertEquals(SlackFileType.PNG, file.getFiletype());
    assertTrue(file instanceof SlackAccessDeniedFile);
  }

  @Test
  public void shouldDeserializeKnownFileType() throws IOException {
    SlackFile file = fetchAndDeserializeSlackFile("file_gif.json");
    assertEquals(SlackFileType.GIF, file.getFiletype());
    assertTrue(file instanceof SlackGifFile);
  }

  @Test
  public void shouldDeserializeUnknownFile() throws IOException {
    SlackFile file = fetchAndDeserializeSlackFile("file_unknown_type.json");
    assertEquals(SlackFileType.UNKNOWN, file.getFiletype());
    assertTrue(file instanceof SlackUnknownFiletype);
    assertEquals("F0S43PZDF", file.getId());
  }

  private SlackFile fetchAndDeserializeSlackFile(String jsonFileName) throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(jsonFileName);
    return ObjectMapperUtils.mapper().readValue(rawJson, SlackFile.class);
  }
}
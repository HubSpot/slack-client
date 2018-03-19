package com.hubspot.slack.client.models.interaction;

import org.junit.Test;

import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;

public class InteractiveActionSerdeTest {
  @Test
  public void itCanDeserButton() throws Exception {
    String raw = JsonLoader.loadJsonFromFile("interactive_button_click.json");
    ObjectMapperUtils.mapper().readValue(raw, InteractiveAction.class);
  }
}

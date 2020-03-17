package com.hubspot.slack.client.models.views;

import java.io.IOException;

import org.junit.Test;

import com.hubspot.slack.client.SerializationTestBase;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.interaction.ViewSubmission;

public class ViewSubmissionSerializationTest extends SerializationTestBase {

  @Test
  public void testViewPayloadSerialization() throws IOException {
    String raw = JsonLoader.loadJsonFromFile("view_submission.json");
    ObjectMapperUtils.mapper().readValue(raw, ViewSubmission.class);
  }
}

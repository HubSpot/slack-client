package com.hubspot.slack.client.models.interaction;

import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import org.junit.Test;

public class DialogSubmissionSerdeTest {

  @Test
  public void itCanDeserDialogSubmission() throws Exception {
    String rawJson = JsonLoader.loadJsonFromFile("dialog_submission.json");
    ObjectMapperUtils.mapper().readValue(rawJson, DialogSubmission.class);
  }
}

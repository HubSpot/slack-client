package com.hubspot.slack.client.models.views;

import java.io.IOException;
import java.util.Map;

import com.hubspot.slack.client.models.interaction.views.ViewCheckboxes;
import com.hubspot.slack.client.models.interaction.views.ViewInput;
import com.hubspot.slack.client.models.interaction.views.ViewInputType;
import org.junit.Test;

import com.hubspot.slack.client.SerializationTestBase;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.interaction.ViewSubmission;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewSubmissionSerializationTest extends SerializationTestBase {

  @Test
  public void testViewPayloadSerialization() throws IOException {
    String raw = JsonLoader.loadJsonFromFile("view_submission.json");
    final ViewSubmission viewSubmission = ObjectMapperUtils.mapper().readValue(raw, ViewSubmission.class);
    final Map<String, Map<String, ViewInput>> bav = viewSubmission.getView().getState().getBlockIdToActionIdToValues();

    // checkboxes
    assertThat(bav.get("nVmE").get("some-checkbox-thing").getType()).isEqualTo(ViewInputType.CHECKBOXES);
    final ViewCheckboxes checkboxes = (ViewCheckboxes) bav.get("nVmE").get("some-checkbox-thing");
    assertThat(checkboxes.getSelectedOptions().size()).isEqualTo(1);
    assertThat(checkboxes.getSelectedOptions().get(0).getText().getText()).isEqualTo("The checkbox label");
    assertThat(checkboxes.getSelectedOptions().get(0).getDescription().isPresent()).isEqualTo(false);
    assertThat(checkboxes.getSelectedOptions().get(0).getValue()).isEqualTo("true");
  }
}

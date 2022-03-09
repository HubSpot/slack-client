package com.hubspot.slack.client.models.views;

import java.io.IOException;
import java.util.Map;

import com.hubspot.slack.client.models.interaction.views.ViewCheckboxes;
import com.hubspot.slack.client.models.interaction.views.ViewInput;
import com.hubspot.slack.client.models.interaction.views.ViewInputType;
import com.hubspot.slack.client.models.interaction.views.ViewMultiStaticSelect;
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

    //multi_static_select
    assertThat(bav.get("YjQ").get("multi_static_select-action").getType()).isEqualTo(ViewInputType.MULTI_STATIC_SELECT);
    final ViewMultiStaticSelect multiStaticSelect = (ViewMultiStaticSelect) bav.get("YjQ").get("multi_static_select-action");
    assertThat(multiStaticSelect.getSelectedOptions().size()).isEqualTo(1);
    assertThat(multiStaticSelect.getSelectedOptions().get(0).getText().getText()).isEqualTo("this is plain_text text");
    assertThat(multiStaticSelect.getSelectedOptions().get(0).getDescription().isPresent()).isEqualTo(false);
    assertThat(multiStaticSelect.getSelectedOptions().get(0).getValue()).isEqualTo("test-1");
  }
}

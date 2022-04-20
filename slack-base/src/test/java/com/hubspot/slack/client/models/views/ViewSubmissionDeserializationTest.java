package com.hubspot.slack.client.models.views;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.blocks.objects.Option;
import com.hubspot.slack.client.models.interaction.ViewSubmission;
import com.hubspot.slack.client.models.interaction.views.ViewCheckboxes;
import com.hubspot.slack.client.models.interaction.views.ViewInput;
import com.hubspot.slack.client.models.interaction.views.ViewInputType;
import com.hubspot.slack.client.models.interaction.views.ViewMultiExternalSelect;
import com.hubspot.slack.client.models.interaction.views.ViewMultiStaticSelect;
import com.hubspot.slack.client.testutils.TestBlocksBuilder;

public class ViewSubmissionDeserializationTest {
  private static Map<String, Map<String, ViewInput>> BLOCK_ID_TO_ACTION_ID_TO_VALUES;

  @BeforeClass
  public static void setUp() throws IOException {
    String raw = JsonLoader.loadJsonFromFile("view_submission.json");
    final ViewSubmission viewSubmission = ObjectMapperUtils.mapper().readValue(raw, ViewSubmission.class);
    BLOCK_ID_TO_ACTION_ID_TO_VALUES = viewSubmission.getView().getState().getBlockIdToActionIdToValues();
  }

  @Test
  public void itDeserializesCheckboxes() {
    ViewInput input = BLOCK_ID_TO_ACTION_ID_TO_VALUES.get("nVmE").get("some-checkbox-thing");
    assertThat(input.getType()).isEqualTo(ViewInputType.CHECKBOXES);
    final ViewCheckboxes checkboxes = (ViewCheckboxes) input;
    assertThat(checkboxes.getSelectedOptions().size()).isEqualTo(1);
    assertThat(checkboxes.getSelectedOptions().get(0).getText().getText()).isEqualTo("The checkbox label");
    assertThat(checkboxes.getSelectedOptions().get(0).getDescription().isPresent()).isEqualTo(false);
    assertThat(checkboxes.getSelectedOptions().get(0).getValue()).isEqualTo("true");
  }

  @Test
  public void itDeserializesMultiStaticSelect() {
    ViewInput input = BLOCK_ID_TO_ACTION_ID_TO_VALUES.get("YjQ").get("multi_static_select-action");
    assertThat(input.getType()).isEqualTo(ViewInputType.MULTI_STATIC_SELECT);

    String optionValue = "test-1";
    String optionText = "Test option 1";
    Option option = TestBlocksBuilder.buildOption(optionValue, optionText);

    String anotherOptionValue = "test-2";
    String anotherOptionText = "Test option 2";
    Option anotherOption = TestBlocksBuilder.buildOption(anotherOptionValue, anotherOptionText);

    final ViewMultiStaticSelect multiStaticSelect = (ViewMultiStaticSelect) input;
    assertThat(multiStaticSelect.getSelectedOptions()).containsExactly(option, anotherOption);
  }

  @Test
  public void itDeserializesMultiExternalSelect() {
    ViewInput input = BLOCK_ID_TO_ACTION_ID_TO_VALUES.get("BjF").get("multi_external_select-action");
    assertThat(input.getType()).isEqualTo(ViewInputType.MULTI_EXTERNAL_SELECT);

    String optionValue = "test-1";
    String optionText = "Test option 1";
    Option option = TestBlocksBuilder.buildOption(optionValue, optionText);

    String anotherOptionValue = "test-2";
    String anotherOptionText = "Test option 2";
    Option anotherOption = TestBlocksBuilder.buildOption(anotherOptionValue, anotherOptionText);

    final ViewMultiExternalSelect multiExternalSelect = (ViewMultiExternalSelect) input;
    assertThat(multiExternalSelect.getSelectedOptions()).containsExactly(option, anotherOption);
  }
}

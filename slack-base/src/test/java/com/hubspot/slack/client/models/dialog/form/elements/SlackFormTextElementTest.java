package com.hubspot.slack.client.models.dialog.form.elements;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.Test;

import com.hubspot.slack.client.testutils.StringGenerator;

public class SlackFormTextElementTest{

  @Test
  public void itNormalizesLongHintToBuildFormSelectElement() {
    SlackFormTextElement optionGroup = SlackFormTextElement.builder()
        .setLabel("ignored")
        .setName("ignored")
        .setHint(StringGenerator.generateStringWithLength(151))
        .build();
    String expectedLabel = StringGenerator.generateStringWithLengthAndEllipsis(146);
    assertThat(optionGroup.getHint()).hasValue(expectedLabel);
  }

  @Test
  public void itFailsToBuildDialogFormElementForMissingTooLongValue() {
    String tooLongValue = StringGenerator.generateStringWithLength(151);
    try {
      SlackFormTextElement.builder().setLabel("ignored").setName("ignored").setValue(tooLongValue).build();
    } catch (IllegalStateException ise) {
      String errorMessage = String.format("Value cannot exceed 150 chars, got %s", tooLongValue);
      assertThat(ise.getMessage()).contains(errorMessage);
      return;
    }

    fail("Didn't throw exception");
  }
}

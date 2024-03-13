package com.hubspot.slack.client.models.dialog.form.elements;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.hubspot.slack.client.testutils.StringGenerator;
import org.junit.Test;

public class SlackFormTextareaElementTest {

  @Test
  public void itNormalizesLongHintToBuildFormSelectElement() {
    SlackFormTextareaElement textareaElement = SlackFormTextareaElement
      .builder()
      .setLabel("ignored")
      .setName("ignored")
      .setHint(StringGenerator.generateStringWithLength(151))
      .build();
    String expectedLabel = StringGenerator.generateStringWithLengthAndEllipsis(146);
    assertThat(textareaElement.getHint()).hasValue(expectedLabel);
  }

  @Test
  public void itFailsToBuildDialogFormElementForMissingTooLongValue() {
    String tooLongValue = StringGenerator.generateStringWithLength(3001);
    try {
      SlackFormTextareaElement
        .builder()
        .setLabel("ignored")
        .setName("ignored")
        .setValue(tooLongValue)
        .build();
    } catch (IllegalStateException ise) {
      String errorMessage = String.format(
        "Value cannot exceed 3000 chars, got %s",
        tooLongValue
      );
      assertThat(ise.getMessage()).contains(errorMessage);
      return;
    }

    fail("Didn't throw exception");
  }
}

package com.hubspot.slack.client.models.dialog.form.elements;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.hubspot.slack.client.testutils.StringGenerator;
import org.junit.Test;

public class SlackDialogFormElementTest {

  @Test
  public void itFailsToBuildDialogFormElementForMissingTooLongName() {
    String tooLongName = StringGenerator.generateStringWithLength(301);
    try {
      SlackFormTextElement.builder().setLabel("ignored").setName(tooLongName).build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage())
        .contains("Name cannot exceed 300 chars, got " + tooLongName);
      return;
    }

    fail("Didn't throw exception");
  }

  @Test
  public void itNormalizesLongLabelToBuildFormSelectElement() {
    SlackFormTextElement slackFormTextElement = SlackFormTextElement
      .builder()
      .setLabel(StringGenerator.generateStringWithLength(25))
      .setName("ignored")
      .build();
    String expectedLabel = StringGenerator.generateStringWithLengthAndEllipsis(20);
    assertThat(slackFormTextElement.getLabel()).isEqualTo(expectedLabel);
  }

  @Test
  public void itNormalizesLongPlaceholderToBuildFormSelectElement() {
    SlackFormTextElement slackFormTextElement = SlackFormTextElement
      .builder()
      .setLabel("ignored")
      .setName("ignored")
      .setPlaceholder(StringGenerator.generateStringWithLength(151))
      .build();
    String expectedLabel = StringGenerator.generateStringWithLengthAndEllipsis(146);
    assertThat(slackFormTextElement.getPlaceholder()).hasValue(expectedLabel);
  }
}

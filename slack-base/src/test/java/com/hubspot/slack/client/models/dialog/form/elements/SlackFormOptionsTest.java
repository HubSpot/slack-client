package com.hubspot.slack.client.models.dialog.form.elements;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.hubspot.slack.client.models.actions.SlackDataSource;
import com.hubspot.slack.client.testutils.StringGenerator;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class SlackFormOptionsTest {

  @Test
  public void itFailsToBuildFormSelectElementForInvalidOptionForStaticDataSource() {
    try {
      SlackFormSelectElement
        .builder()
        .setName("test-select")
        .setLabel("test-select-label")
        .setDataSource(SlackDataSource.STATIC)
        .build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage())
        .contains(
          "Either options or option groups are required for static data source types"
        );
      return;
    }

    fail("Didn't throw exception");
  }

  @Test
  public void itFailsToBuildFormSelectElementForInvalidValueForExternalDataSource() {
    try {
      SlackFormSelectElement
        .builder()
        .setName("test-select")
        .setLabel("test-select-label")
        .setValue("value")
        .setDataSource(SlackDataSource.EXTERNAL)
        .build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage())
        .contains("Cannot use value for external data source, must use selected options");
      return;
    }

    fail("Didn't throw exception");
  }

  @Test
  public void itFailsToBuildFormSelectElementForInvalidValue() {
    try {
      SlackFormSelectElement
        .builder()
        .setName("test-select")
        .setLabel("test-select-label")
        .setValue("value")
        .addOptions(
          SlackFormOption.builder().setLabel("label").setValue("not value").build()
        )
        .build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage())
        .contains("Value must exactly match the value field for one provided option");
      return;
    }

    fail("Didn't throw exception");
  }

  @Test
  public void itFailsToBuildFormSelectElementForMultipleSelectedOptions() {
    try {
      SlackFormSelectElement
        .builder()
        .setDataSource(SlackDataSource.EXTERNAL)
        .setName("test-select")
        .setLabel("test-select-label")
        .addSelectedOptions(
          SlackFormOption.builder().setLabel("label1").setValue("value1").build()
        )
        .addSelectedOptions(
          SlackFormOption.builder().setLabel("label2").setValue("value2").build()
        )
        .build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage())
        .contains("Selected options must be a single element array");
      return;
    }

    fail("Didn't throw exception");
  }

  @Test
  public void itFailsToBuildFormSelectElementForSelectedOptionNotMatchingOptionGroups() {
    try {
      SlackFormSelectElement
        .builder()
        .setDataSource(SlackDataSource.EXTERNAL)
        .setName("test-select")
        .setLabel("test-select-label")
        .addSelectedOptions(
          SlackFormOption.builder().setLabel("label1").setValue("value1").build()
        )
        .addOptionGroups(
          SlackFormOptionGroup
            .builder()
            .setLabel("group1")
            .addOptions(
              SlackFormOption.builder().setLabel("label2").setValue("value2").build()
            )
            .build()
        )
        .build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage())
        .contains(
          "Selected option must exactly match an option in the provided options groups"
        );
      return;
    }

    fail("Didn't throw exception");
  }

  @Test
  public void itFailsToBuildFormSelectElementForSelectedOptionNotMatchingOptions() {
    try {
      SlackFormSelectElement
        .builder()
        .setDataSource(SlackDataSource.EXTERNAL)
        .setName("test-select")
        .setLabel("test-select-label")
        .addSelectedOptions(
          SlackFormOption.builder().setLabel("label1").setValue("value1").build()
        )
        .addOptions(
          SlackFormOption.builder().setLabel("label2").setValue("value2").build()
        )
        .build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage())
        .contains("Selected option must exactly match an option in the provided options");
      return;
    }

    fail("Didn't throw exception");
  }

  @Test
  public void itReturnsAllErrorsWhenFailingToBuildFormSelectElements() {
    try {
      SlackFormSelectElement
        .builder()
        .setDataSource(SlackDataSource.EXTERNAL)
        .setName("test-select")
        .setLabel("test-select-label")
        .addSelectedOptions(
          SlackFormOption.builder().setLabel("label1").setValue("value1").build()
        )
        .addSelectedOptions(
          SlackFormOption.builder().setLabel("label2").setValue("value2").build()
        )
        .addOptions(
          SlackFormOption.builder().setLabel("label3").setValue("value3").build()
        )
        .build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage())
        .contains("Selected options must be a single element array")
        .contains("Selected option must exactly match an option in the provided options");
      return;
    }

    fail("Didn't throw exception");
  }

  @Test
  public void itFailsToBuildFormSelectOptionGroupForMissingLabel() {
    try {
      SlackFormOptionGroup
        .builder()
        .setLabel("") // missing label
        .addOptions(SlackFormOption.builder().setLabel("label").setValue("value").build())
        .build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage()).contains("Must provide a label");
      return;
    }

    fail("Didn't throw exception");
  }

  @Test
  public void itNormalizesLongLabelToBuildFormSelectOptionGroup() {
    SlackFormOptionGroup optionGroup = SlackFormOptionGroup
      .builder()
      .setLabel(StringGenerator.generateStringWithLength(76))
      .addOptions(SlackFormOption.builder().setLabel("label").setValue("value").build())
      .build();
    String expectedLabel = StringGenerator.generateStringWithLengthAndEllipsis(71);
    assertThat(optionGroup.getLabel()).isEqualTo(expectedLabel);
  }

  @Test
  public void itNormalizesLongLabelToBuildFormSelectOption() {
    SlackFormOption option = SlackFormOption
      .builder()
      .setLabel(StringGenerator.generateStringWithLength(76))
      .setValue("value-1")
      .build();
    String expectedLabel = StringGenerator.generateStringWithLengthAndEllipsis(71);
    assertThat(option.getLabel()).isEqualTo(expectedLabel);
  }

  @Test
  public void itNormalizesOptionsListToBuildFormSelectOptionGroup() {
    List<SlackFormOption> options = new ArrayList<>();

    for (int i = 0; i < 101; i++) {
      options.add(
        SlackFormOption
          .builder()
          .setLabel("label-" + String.valueOf(i))
          .setValue("value-" + String.valueOf(i))
          .build()
      );
    }

    SlackFormOptionGroup optionGroup = SlackFormOptionGroup
      .builder()
      .setLabel("label")
      .setOptions(options)
      .build();
    assertThat(optionGroup.getOptions()).hasSize(100);
  }

  @Test
  public void itNormalizesOptionsListToBuildFormSelectElement() {
    List<SlackFormOption> options = new ArrayList<>();

    for (int i = 0; i < 101; i++) {
      options.add(
        SlackFormOption
          .builder()
          .setLabel("label-" + String.valueOf(i))
          .setValue("value-" + String.valueOf(i))
          .build()
      );
    }

    SlackFormSelectElement slackFormSelectElement = SlackFormSelectElement
      .builder()
      .setLabel("ignored")
      .setName("ignored")
      .setOptions(options)
      .build();
    assertThat(slackFormSelectElement.getOptions()).hasSize(100);
  }
}

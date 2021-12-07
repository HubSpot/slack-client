package com.hubspot.slack.client.models.dialog.form.elements;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.hubspot.slack.client.models.actions.SlackDataSource;

public class SlackFormOptionsTest {

  @Test
  public void itFailsToBuildFormSelectElementForInvalidOptionForStaticDataSource() {
    try {
      SlackFormSelectElement.builder()
          .setName("test-select")
          .setLabel("test-select-label")
          .setDataSource(SlackDataSource.STATIC)
          .build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage()).contains("Either options or option groups are required for static data source types");
      return;
    }

    fail("Didn't throw exception");
  }

  @Test
  public void itFailsToBuildFormSelectElementForInvalidValueForExternalDataSource() {
    try {
      SlackFormSelectElement.builder()
          .setName("test-select")
          .setLabel("test-select-label")
          .setValue("value")
          .setDataSource(SlackDataSource.EXTERNAL)
          .build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage()).contains("Cannot use value for external data source, must use selected options");
      return;
    }

    fail("Didn't throw exception");
  }

  @Test
  public void itFailsToBuildFormSelectElementForInvalidValue() {
    try {
      SlackFormSelectElement.builder()
          .setName("test-select")
          .setLabel("test-select-label")
          .setValue("value")
          .addOptions(SlackFormOption.builder()
              .setLabel("label")
              .setValue("not value")
              .build())
          .build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage()).contains("Value must exactly match the value field for one provided option");
      return;
    }

    fail("Didn't throw exception");
  }

  @Test
  public void itFailsToBuildFormSelectElementForMultipleSelectedOptions() {
    try {
      SlackFormSelectElement.builder()
          .setDataSource(SlackDataSource.EXTERNAL)
          .setName("test-select")
          .setLabel("test-select-label")
          .addSelectedOptions(SlackFormOption.builder().setLabel("label1").setValue("value1").build())
          .addSelectedOptions(SlackFormOption.builder().setLabel("label2").setValue("value2").build())
          .build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage()).contains("Selected options must be a single element array");
      return;
    }

    fail("Didn't throw exception");
  }

  @Test
  public void itFailsToBuildFormSelectElementForSelectedOptionNotMatchingOptionGroups() {
    try {
      SlackFormSelectElement.builder()
          .setDataSource(SlackDataSource.EXTERNAL)
          .setName("test-select")
          .setLabel("test-select-label")
          .addSelectedOptions(SlackFormOption.builder().setLabel("label1").setValue("value1").build())
          .addOptionGroups(SlackFormOptionGroup.builder()
              .setLabel("group1")
              .addOptions(SlackFormOption.builder()
                  .setLabel("label2")
                  .setValue("value2")
                  .build())
              .build())
          .build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage()).contains("Selected option must exactly match an option in the provided options groups");
      return;
    }

    fail("Didn't throw exception");
  }

  @Test
  public void itFailsToBuildFormSelectElementForSelectedOptionNotMatchingOptions() {
    try {
      SlackFormSelectElement.builder()
          .setDataSource(SlackDataSource.EXTERNAL)
          .setName("test-select")
          .setLabel("test-select-label")
          .addSelectedOptions(SlackFormOption.builder().setLabel("label1").setValue("value1").build())
          .addOptions(SlackFormOption.builder()
              .setLabel("label2")
              .setValue("value2")
              .build())
          .build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage()).contains("Selected option must exactly match an option in the provided options");
      return;
    }

    fail("Didn't throw exception");
  }

  @Test
  public void itReturnsAllErrorsWhenFailingToBuildFormSelectElements() {
    try {
      SlackFormSelectElement.builder()
          .setDataSource(SlackDataSource.EXTERNAL)
          .setName("test-select")
          .setLabel("test-select-label")
          .addSelectedOptions(SlackFormOption.builder().setLabel("label1").setValue("value1").build())
          .addSelectedOptions(SlackFormOption.builder().setLabel("label2").setValue("value2").build())
          .addOptions(SlackFormOption.builder()
              .setLabel("label3")
              .setValue("value3")
              .build())
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
      SlackFormOptionGroup.builder()
          .setLabel("") // missing label
          .addOptions(SlackFormOption.builder()
              .setLabel("label")
              .setValue("value")
              .build()
          ).build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage()).contains("Must provide a label");
      return;
    }

    fail("Didn't throw exception");
  }

  @Test
  public void itFailsToBuildFormSelectOptionGroupForLongLabel() {
    try {
      SlackFormOptionGroup.builder()
          .setLabel(String.join("", Collections.nCopies(76, "a"))) // long label
          .addOptions(SlackFormOption.builder()
              .setLabel("label")
              .setValue("value")
              .build()
          ).build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage()).contains("Label cannot exceed 75 chars");
      return;
    }

    fail("Didn't throw exception");
  }

  @Test
  public void itFailsToBuildFormSelectOptionGroupForInvalidNumberOfOptions() {
    List<SlackFormOption> options = new ArrayList<>();

    for (int i = 0; i < 101; i++) {
      options.add(SlackFormOption.builder()
          .setLabel("label-" + String.valueOf(i))
          .setValue("value-" + String.valueOf(i))
          .build());
    }

    try {
      SlackFormOptionGroup.builder()
          .setLabel("label")
          .setOptions(options)
          .build();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage()).contains("Cannot have more than 100 option groups");
      return;
    }

    fail("Didn't throw exception");
  }
}

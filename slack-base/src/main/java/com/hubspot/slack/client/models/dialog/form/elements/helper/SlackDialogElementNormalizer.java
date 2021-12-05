package com.hubspot.slack.client.models.dialog.form.elements.helper;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Iterables;
import com.hubspot.slack.client.models.dialog.form.elements.AbstractSlackDialogFormTextElement;
import com.hubspot.slack.client.models.dialog.form.elements.AbstractSlackFormSelectElement;
import com.hubspot.slack.client.models.dialog.form.elements.AbstractSlackFormTextElement;
import com.hubspot.slack.client.models.dialog.form.elements.AbstractSlackFormTextareaElement;
import com.hubspot.slack.client.models.dialog.form.elements.HasLabel;
import com.hubspot.slack.client.models.dialog.form.elements.SlackDialogFormElement;
import com.hubspot.slack.client.models.dialog.form.elements.SlackDialogFormElementLengthLimits;
import com.hubspot.slack.client.models.dialog.form.elements.SlackFormOption;
import com.hubspot.slack.client.models.dialog.form.elements.SlackFormOptionGroup;
import com.hubspot.slack.client.models.dialog.form.elements.SlackFormOptionGroupIF;
import com.hubspot.slack.client.models.dialog.form.elements.SlackFormOptionIF;
import com.hubspot.slack.client.models.dialog.form.elements.SlackFormSelectElement;
import com.hubspot.slack.client.models.dialog.form.elements.SlackFormTextElement;
import com.hubspot.slack.client.models.dialog.form.elements.SlackFormTextareaElement;

public class SlackDialogElementNormalizer {
  private SlackDialogElementNormalizer() {
  }

  public static AbstractSlackFormTextElement normalize(AbstractSlackFormTextElement element) {
    if (shouldNormalizePlaceholder(element, SlackDialogFormElementLengthLimits.MAX_PLACEHOLDER_LENGTH)
        || shouldNormalizeLabel(element, SlackDialogFormElementLengthLimits.MAX_LABEL_LENGTH)
        || shouldNormalizeName(element)
        || shouldNormalize(element.getHint(), SlackDialogFormElementLengthLimits.MAX_HINT_LENGTH)
        || shouldNormalize(element.getValue(), SlackDialogFormElementLengthLimits.MAX_TEXT_ELEMENT_VALUE_LENGTH)) {
      return SlackFormTextElement.copyOf(element)
          .withPlaceholder(normalizePlaceholder(element))
          .withLabel(normalizeLabel(element))
          .withName(normalizeName(element))
          .withHint(normalizeHint(element))
          .withValue(normalizeTextElementValue(element));
    }
    return element;
  }

  public static AbstractSlackFormTextareaElement normalize(AbstractSlackFormTextareaElement element) {
    if (shouldNormalizePlaceholder(element, SlackDialogFormElementLengthLimits.MAX_PLACEHOLDER_LENGTH)
        || shouldNormalizeLabel(element, SlackDialogFormElementLengthLimits.MAX_LABEL_LENGTH)
        || shouldNormalizeName(element)
        || shouldNormalize(element.getHint(), SlackDialogFormElementLengthLimits.MAX_HINT_LENGTH)
        || shouldNormalize(element.getValue(), SlackDialogFormElementLengthLimits.MAX_TEXT_AREA_ELEMENT_VALUE_LENGTH)) {
      return SlackFormTextareaElement.copyOf(element)
          .withPlaceholder(normalizePlaceholder(element))
          .withLabel(normalizeLabel(element))
          .withName(normalizeName(element))
          .withHint(normalizeHint(element))
          .withValue(normalizeTextAreaElementValue(element));
    }
    return element;
  }

  public static AbstractSlackFormSelectElement normalize(AbstractSlackFormSelectElement element) {
    if (shouldNormalizePlaceholder(element, SlackDialogFormElementLengthLimits.MAX_PLACEHOLDER_LENGTH)
        || shouldNormalizeLabel(element, SlackDialogFormElementLengthLimits.MAX_LABEL_LENGTH)
        || shouldNormalizeName(element)
        || shouldNormalize(element.getOptionGroups(), SlackDialogFormElementLengthLimits.MAX_OPTION_GROUPS_NUMBER)
        || shouldNormalize(element.getOptions(), SlackDialogFormElementLengthLimits.MAX_OPTIONS_NUMBER)) {
      return SlackFormSelectElement.copyOf(element)
          .withPlaceholder(normalizePlaceholder(element))
          .withLabel(normalizeLabel(element))
          .withName(normalizeName(element))
          .withOptionGroups(normalizeOptionGroups(element))
          .withOptions(normalizeOptions(element));
    }
    return element;
  }

  public static SlackFormOptionIF normalize(SlackFormOptionIF element) {
    if (shouldNormalizeLabel(element, SlackDialogFormElementLengthLimits.MAX_OPTION_LABEL_LENGTH)
        || shouldNormalize(element.getValue(), SlackDialogFormElementLengthLimits.MAX_OPTION_VALUE_LENGTH)) {
      return SlackFormOption.copyOf(element)
          .withLabel(normalizeLabel(element))
          .withValue(normalizeValue(element));
    }
    return element;
  }

  public static SlackFormOptionGroupIF normalize(SlackFormOptionGroupIF element) {
    if (shouldNormalizeLabel(element, SlackDialogFormElementLengthLimits.MAX_OPTION_LABEL_LENGTH)
        || shouldNormalize(element.getOptions(), SlackDialogFormElementLengthLimits.MAX_OPTIONS_NUMBER)) {
      return SlackFormOptionGroup.builder()
          .from(element)
          .setLabel(normalizeLabel(element))
          .setOptions(normalizeOptions(element))
          .build();
    }
    return element;
  }

  private static boolean shouldNormalize(List listOfFormElements, SlackDialogFormElementLengthLimits maxListSize) {
    return listOfFormElements.size() > maxListSize.getLimit();
  }

  private static boolean shouldNormalizeName(SlackDialogFormElement element) {
    return shouldNormalize(element.getName(), SlackDialogFormElementLengthLimits.MAX_NAME_LENGTH);
  }

  private static boolean shouldNormalizePlaceholder(SlackDialogFormElement element,
                                                    SlackDialogFormElementLengthLimits maxLengthLimit) {
    return shouldNormalize(element.getPlaceholder(), maxLengthLimit);
  }

  private static boolean shouldNormalizeLabel(HasLabel element, SlackDialogFormElementLengthLimits maxLabelLength) {
    return shouldNormalize(element.getLabel(), maxLabelLength);
  }

  private static boolean shouldNormalize(Optional<String> stringOptional,
                                         SlackDialogFormElementLengthLimits maxPlaceholderLength) {
    return stringOptional.isPresent() && shouldNormalize(stringOptional.get(), maxPlaceholderLength);
  }

  private static boolean shouldNormalize(String string,
                                         SlackDialogFormElementLengthLimits maxPlaceholderLength) {
    return string.length() > maxPlaceholderLength.getLimit();
  }

  private static String normalizeLabel(SlackFormOptionIF option) {
    return normalizeIfLongerThan(option.getLabel(), SlackDialogFormElementLengthLimits.MAX_OPTION_LABEL_LENGTH.getLimit());
  }

  private static String normalizeValue(SlackFormOptionIF option) {
    return normalizeIfLongerThan(option.getValue(), SlackDialogFormElementLengthLimits.MAX_OPTION_VALUE_LENGTH.getLimit());
  }

  private static String normalizeLabel(SlackFormOptionGroupIF optionGroup) {
    return normalizeIfLongerThan(optionGroup.getLabel(), SlackDialogFormElementLengthLimits.MAX_OPTION_LABEL_LENGTH.getLimit());
  }

  private static Iterable<SlackFormOptionGroup> normalizeOptionGroups(AbstractSlackFormSelectElement element) {
    return Iterables.limit(element.getOptionGroups(), SlackDialogFormElementLengthLimits.MAX_OPTION_GROUPS_NUMBER.getLimit());
  }

  private static Iterable<SlackFormOption> normalizeOptions(AbstractSlackFormSelectElement element) {
    return Iterables.limit(element.getOptions(), SlackDialogFormElementLengthLimits.MAX_OPTIONS_NUMBER.getLimit());
  }

  private static Iterable<SlackFormOption> normalizeOptions(SlackFormOptionGroupIF element) {
    return Iterables.limit(element.getOptions(), SlackDialogFormElementLengthLimits.MAX_OPTIONS_NUMBER.getLimit());
  }

  private static String normalizeLabel(SlackDialogFormElement element) {
    return normalizeIfLongerThan(element.getLabel(), SlackDialogFormElementLengthLimits.MAX_LABEL_LENGTH.getLimit());
  }

  private static String normalizeName(SlackDialogFormElement element) {
    return normalizeIfLongerThan(element.getName(), SlackDialogFormElementLengthLimits.MAX_NAME_LENGTH.getLimit());
  }

  private static Optional<String> normalizePlaceholder(SlackDialogFormElement element) {
    return element.getPlaceholder()
        .map(placeholder ->
            normalizeIfLongerThan(
                placeholder,
                SlackDialogFormElementLengthLimits.MAX_PLACEHOLDER_LENGTH.getLimit()
            ));
  }

  private static Optional<String> normalizeHint(AbstractSlackDialogFormTextElement element) {
    return element.getHint()
        .map(hint -> normalizeIfLongerThan(hint, SlackDialogFormElementLengthLimits.MAX_HINT_LENGTH.getLimit()));
  }

  private static Optional<String> normalizeTextElementValue(AbstractSlackFormTextElement element) {
    return element.getValue()
        .map(value ->
            normalizeIfLongerThan(
                value,
                SlackDialogFormElementLengthLimits.MAX_TEXT_ELEMENT_VALUE_LENGTH.getLimit()
            ));
  }

  private static Optional<String> normalizeTextAreaElementValue(AbstractSlackFormTextareaElement element) {
    return element.getValue()
        .map(value ->
            normalizeIfLongerThan(
                value,
                SlackDialogFormElementLengthLimits.MAX_TEXT_AREA_ELEMENT_VALUE_LENGTH.getLimit()
            ));
  }

  private static String normalizeIfLongerThan(String label, int maxLength) {
    if (label.length() > maxLength) {
      String ellipsis = "...";
      int endIndex = (maxLength - ellipsis.length()) - 1;
      return label.substring(0, endIndex) + ellipsis;
    }
    return label;
  }
}

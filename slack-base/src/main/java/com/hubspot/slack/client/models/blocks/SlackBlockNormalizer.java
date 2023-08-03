package com.hubspot.slack.client.models.blocks;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Iterables;
import com.hubspot.slack.client.models.blocks.elements.Checkboxes;
import com.hubspot.slack.client.models.blocks.elements.CheckboxesIF;
import com.hubspot.slack.client.models.blocks.elements.EmailInput;
import com.hubspot.slack.client.models.blocks.elements.EmailInputIF;
import com.hubspot.slack.client.models.blocks.elements.NumberInput;
import com.hubspot.slack.client.models.blocks.elements.NumberInputIF;
import com.hubspot.slack.client.models.blocks.elements.PlainTextInput;
import com.hubspot.slack.client.models.blocks.elements.PlainTextInputIF;
import com.hubspot.slack.client.models.blocks.elements.RadioButtonGroup;
import com.hubspot.slack.client.models.blocks.elements.RadioButtonGroupIF;
import com.hubspot.slack.client.models.blocks.elements.StaticMultiSelectMenu;
import com.hubspot.slack.client.models.blocks.elements.StaticMultiSelectMenuIF;
import com.hubspot.slack.client.models.blocks.elements.StaticSelectMenu;
import com.hubspot.slack.client.models.blocks.elements.StaticSelectMenuIF;
import com.hubspot.slack.client.models.blocks.elements.UrlInput;
import com.hubspot.slack.client.models.blocks.elements.UrlInputIF;
import com.hubspot.slack.client.models.blocks.objects.Option;
import com.hubspot.slack.client.models.blocks.objects.OptionGroup;
import com.hubspot.slack.client.models.blocks.objects.OptionGroupIF;
import com.hubspot.slack.client.models.blocks.objects.OptionIF;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.dialog.form.elements.SlackDialogFormElementLengthLimits;
import com.hubspot.slack.client.models.interaction.BlocksLoadOptionsResponse;
import com.hubspot.slack.client.models.interaction.BlocksLoadOptionsResponseIF;

public class SlackBlockNormalizer {
  private SlackBlockNormalizer() {
  }

  public static InputIF normalize(InputIF input) {
    if (shouldNormalize(input.getLabel(), BlockElementLengthLimits.MAX_INPUT_LABEL_LENGTH)
        || shouldNormalize(input.getHint(), BlockElementLengthLimits.MAX_HINT_LENGTH)) {
      return Input.builder()
          .from(input)
          .setLabel(normalize(input.getLabel(), BlockElementLengthLimits.MAX_INPUT_LABEL_LENGTH))
          .setHint(normalize(input.getHint(), BlockElementLengthLimits.MAX_HINT_LENGTH))
          .build();
    }
    return input;
  }

  public static PlainTextInputIF normalize(PlainTextInputIF plainTextInput) {
    if (shouldNormalize(plainTextInput.getPlaceholder(), BlockElementLengthLimits.MAX_PLAIN_TEXT_PLACEHOLDER_LENGTH)) {
      return PlainTextInput.builder()
          .from(plainTextInput)
          .setPlaceholder(normalize(plainTextInput.getPlaceholder(), BlockElementLengthLimits.MAX_PLAIN_TEXT_PLACEHOLDER_LENGTH))
          .build();
    }
    return plainTextInput;
  }

  public static NumberInputIF normalize(NumberInputIF numberInput) {
    if (shouldNormalize(numberInput.getPlaceholder(), BlockElementLengthLimits.MAX_PLAIN_TEXT_PLACEHOLDER_LENGTH)) {
      return NumberInput.builder()
              .from(numberInput)
              .setPlaceholder(normalize(numberInput.getPlaceholder(), BlockElementLengthLimits.MAX_PLAIN_TEXT_PLACEHOLDER_LENGTH))
              .build();
    }
    return numberInput;
  }

  public static EmailInputIF normalize(EmailInputIF emailInput) {
    if (shouldNormalize(emailInput.getPlaceholder(), BlockElementLengthLimits.MAX_PLAIN_TEXT_PLACEHOLDER_LENGTH)) {
      return EmailInput.builder()
              .from(emailInput)
              .setPlaceholder(normalize(emailInput.getPlaceholder(), BlockElementLengthLimits.MAX_PLAIN_TEXT_PLACEHOLDER_LENGTH))
              .build();
    }
    return emailInput;
  }

  public static UrlInputIF normalize(UrlInputIF urlInput) {
    if (shouldNormalize(urlInput.getPlaceholder(), BlockElementLengthLimits.MAX_PLAIN_TEXT_PLACEHOLDER_LENGTH)) {
      return UrlInput.builder()
              .from(urlInput)
              .setPlaceholder(normalize(urlInput.getPlaceholder(), BlockElementLengthLimits.MAX_PLAIN_TEXT_PLACEHOLDER_LENGTH))
              .build();
    }
    return urlInput;
  }

  public static StaticMultiSelectMenuIF normalize(StaticMultiSelectMenuIF staticMultiSelectMenu) {
    if (shouldNormalize(staticMultiSelectMenu.getOptionGroups(), BlockElementLengthLimits.MAX_OPTION_GROUPS_NUMBER)
        || shouldNormalize(staticMultiSelectMenu.getOptions(), BlockElementLengthLimits.MAX_OPTIONS_NUMBER)) {
      return StaticMultiSelectMenu.builder()
          .from(staticMultiSelectMenu)
          .setOptionGroups(normalizeOptionGroups(staticMultiSelectMenu.getOptionGroups()))
          .setOptions(normalizeOptions(staticMultiSelectMenu.getOptions()))
          .build();
    }
    return staticMultiSelectMenu;
  }

  public static StaticSelectMenuIF normalize(StaticSelectMenuIF staticSelectMenu) {
    if (shouldNormalize(staticSelectMenu.getOptionGroups(), BlockElementLengthLimits.MAX_OPTION_GROUPS_NUMBER)
        || shouldNormalize(staticSelectMenu.getOptions(), BlockElementLengthLimits.MAX_OPTIONS_NUMBER)) {
      return StaticSelectMenu.builder()
          .from(staticSelectMenu)
          .setOptionGroups(normalizeOptionGroups(staticSelectMenu.getOptionGroups()))
          .setOptions(normalizeOptions(staticSelectMenu.getOptions()))
          .build();
    }
    return staticSelectMenu;
  }

  public static BlocksLoadOptionsResponseIF normalize(BlocksLoadOptionsResponseIF blocksLoadOptionsResponse) {
    if (shouldNormalize(blocksLoadOptionsResponse.getOptionGroups(), BlockElementLengthLimits.MAX_OPTION_GROUPS_NUMBER)
        || shouldNormalize(blocksLoadOptionsResponse.getOptions(), BlockElementLengthLimits.MAX_OPTIONS_NUMBER)) {
      return BlocksLoadOptionsResponse.builder()
          .from(blocksLoadOptionsResponse)
          .setOptionGroups(normalizeOptionGroups(blocksLoadOptionsResponse.getOptionGroups()))
          .setOptions(normalizeOptions(blocksLoadOptionsResponse.getOptions()))
          .build();
    }
    return blocksLoadOptionsResponse;
  }

  public static OptionGroupIF normalize(OptionGroupIF optionGroup) {
    if (shouldNormalize(optionGroup.getOptions(), BlockElementLengthLimits.MAX_OPTIONS_NUMBER)
        || shouldNormalize(optionGroup.getLabel(), BlockElementLengthLimits.MAX_OPTION_GROUP_LABEL_LENGTH)) {
      return OptionGroup.builder()
          .from(optionGroup)
          .setOptions(normalizeOptions(optionGroup.getOptions()))
          .setLabel(normalize(optionGroup.getLabel(), BlockElementLengthLimits.MAX_OPTION_GROUP_LABEL_LENGTH))
          .build();
    }
    return optionGroup;
  }

  public static OptionIF normalize(OptionIF option) {
    if (shouldNormalize(option.getText(), BlockElementLengthLimits.MAX_OPTION_TEXT_LENGTH)) {
      return Option.builder()
          .from(option)
          .setText(normalize(option.getText(), BlockElementLengthLimits.MAX_OPTION_TEXT_LENGTH))
          .build();
    }
    return option;
  }

  public static RadioButtonGroupIF normalize(RadioButtonGroupIF radioButtonGroup) {
    if (shouldNormalize(radioButtonGroup.getOptions(), BlockElementLengthLimits.MAX_RADIO_BUTTONS_NUMBER)) {
      return RadioButtonGroup.builder()
          .from(radioButtonGroup)
          .setOptions(normalizeOptions(radioButtonGroup.getOptions(), BlockElementLengthLimits.MAX_RADIO_BUTTONS_NUMBER))
          .build();
    }
    return radioButtonGroup;
  }

  public static CheckboxesIF normalize(CheckboxesIF checkboxes) {
    if (shouldNormalize(checkboxes.getOptions(), BlockElementLengthLimits.MAX_CHECKBOXES_NUMBER)) {
      return Checkboxes.builder()
          .from(checkboxes)
          .setOptions(normalizeOptions(checkboxes.getOptions(), BlockElementLengthLimits.MAX_CHECKBOXES_NUMBER))
          .build();
    }
    return checkboxes;
  }

  private static boolean shouldNormalize(Text text, BlockElementLengthLimits lengthLimit) {
    return text.getText().length() > lengthLimit.getLimit();
  }

  private static boolean shouldNormalize(Optional<Text> textMaybe, BlockElementLengthLimits lengthLimit) {
    return textMaybe.filter(text -> shouldNormalize(text, lengthLimit)).isPresent();
  }

  private static boolean shouldNormalize(List listOfFormElements, BlockElementLengthLimits maxListSize) {
    return listOfFormElements.size() > maxListSize.getLimit();
  }

  private static Iterable<OptionGroup> normalizeOptionGroups(Iterable<OptionGroup> optionGroups) {
    return Iterables.limit(optionGroups, SlackDialogFormElementLengthLimits.MAX_OPTION_GROUPS_NUMBER.getLimit());
  }

  private static Iterable<Option> normalizeOptions(List<Option> options) {
    return normalizeOptions(options, BlockElementLengthLimits.MAX_OPTIONS_NUMBER);
  }

  private static Iterable<Option> normalizeOptions(List<Option> options, BlockElementLengthLimits lengthLimit) {
    return Iterables.limit(options, lengthLimit.getLimit());
  }

  private static Optional<Text> normalize(Optional<Text> textMaybe, BlockElementLengthLimits lengthLimit) {
    return textMaybe.map(text -> normalize(text, lengthLimit));
  }

  private static Text normalize(Text text, BlockElementLengthLimits lengthLimit) {
    return Text.copyOf(text).withText(normalizeIfLongerThan(text.getText(), lengthLimit.getLimit()));
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

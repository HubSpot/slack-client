package com.hubspot.slack.client.examples;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import com.hubspot.slack.client.models.blocks.Context;
import com.hubspot.slack.client.models.blocks.Divider;
import com.hubspot.slack.client.models.blocks.Section;
import com.hubspot.slack.client.models.blocks.elements.Button;
import com.hubspot.slack.client.models.blocks.elements.StaticSelectMenu;
import com.hubspot.slack.client.models.blocks.objects.Option;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;

public class Blocks {
  private Blocks() {
  }

  public static Section markdownSection(String text) {
    return Section.builder()
        .setText(markdownText(text))
        .build();
  }

  public static Context markdownContext(String text) {
    return Context.builder()
        .setElements(Collections.singleton(markdownText(text)))
        .build();
  }

  public static Divider divider() {
    return Divider.builder().build();
  }

  public static Button plainTextButton(String text) {
    return Button.builder()
        .setActionId(UUID.randomUUID().toString())
        .setText(plainText(text))
        .build();
  }

  public static Text markdownText(String text) {
    return Text.builder()
        .setText(text)
        .setType(TextType.MARKDOWN)
        .build();
  }

  public static StaticSelectMenu staticSelectMenu(String placeholder, Option... options) {
    return StaticSelectMenu.builder()
        .setActionId(UUID.randomUUID().toString())
        .setPlaceholder(plainText(placeholder))
        .setOptions(Arrays.asList(options))
        .build();
  }

  public static Option option(String text, String value) {
    return Option.builder()
        .setText(plainText(text))
        .setValue(value)
        .build();
  }

  public static Text plainText(String text) {
    return Text.builder()
        .setText(text)
        .setType(TextType.PLAIN_TEXT)
        .build();
  }

}

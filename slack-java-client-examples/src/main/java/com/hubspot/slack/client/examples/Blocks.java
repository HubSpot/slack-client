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
    return Section.of(markdownText(text));
  }

  public static Context markdownContext(String text) {
    return Context.of(Collections.singleton(markdownText(text)));
  }

  public static Divider divider() {
    return Divider.builder().build();
  }

  public static Button plainTextButton(String text) {
    return Button.of(plainText(text), UUID.randomUUID().toString());
  }

  public static Text markdownText(String text) {
    return Text.of(TextType.MARKDOWN, text);
  }

  public static StaticSelectMenu staticSelectMenu(String placeholder, Option... options) {
    return StaticSelectMenu.of(plainText(placeholder), UUID.randomUUID().toString(), Arrays.asList(options));
  }

  public static Option option(String text, String value) {
    return Option.of(plainText(text), value);
  }

  public static Text plainText(String text) {
    return Text.of(TextType.PLAIN_TEXT, text);
  }
}

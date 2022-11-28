package com.hubspot.slack.client.testutils;

import com.hubspot.slack.client.models.blocks.objects.Option;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;

public class TestBlocksBuilder {

  private TestBlocksBuilder() {
  }

  public static Option buildOption(String value, String text) {
    return Option.builder()
        .setValue(value)
        .setText(buildPlainText(text))
        .build();
  }

  private static Text buildPlainText(String text) {
    return Text.builder()
        .setText(text)
        .setType(TextType.PLAIN_TEXT)
        .setEmoji(true)
        .build();
  }
}

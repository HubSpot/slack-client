package com.hubspot.slack.client.testutils;

import com.hubspot.slack.client.models.blocks.Block;
import com.hubspot.slack.client.models.blocks.messages.ListType;
import com.hubspot.slack.client.models.blocks.messages.RichTextList;
import com.hubspot.slack.client.models.blocks.messages.RichTextPreformatted;
import com.hubspot.slack.client.models.blocks.messages.RichTextQuote;
import com.hubspot.slack.client.models.blocks.messages.RichTextSection;
import com.hubspot.slack.client.models.blocks.messages.TextStyle;
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

  public static com.hubspot.slack.client.models.blocks.messages.Text buildMessageBlockText(String text) {
    return com.hubspot.slack.client.models.blocks.messages.Text.builder()
            .setText(text)
            .build();
  }

  public static com.hubspot.slack.client.models.blocks.messages.Text buildMessageBlockTextWithStyle(String text, TextStyle style) {
    return buildMessageBlockText(text).withStyle(style);
  }

  public static RichTextSection buildTextSection(Block... elements) {
    return RichTextSection.builder()
            .addElements(elements)
            .build();
  }

  public static RichTextList buildList(int indent, int border, ListType listType, Block ... elements) {
    return RichTextList.builder()
            .setIndent(indent)
            .setBorder(border)
            .setStyle(listType)
            .addElements(elements)
            .build();
  }

  public static RichTextQuote buildQuote(Block ... elements) {
    return RichTextQuote.builder()
            .addElements(elements)
            .build();
  }

  public static RichTextPreformatted buildPreformattedText(Block ... elements) {
    return RichTextPreformatted.builder().addElements(elements).build();
  }
}

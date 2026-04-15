package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import java.util.Optional;

/**
 * Slack's markdown block type renders standard Markdown natively (e.g. **bold**, ## headings).
 * This avoids needing to convert LLM output (which is standard Markdown) to Slack markdown syntax.
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/markdown-block">Slack Markdown Block Docs</a>
 */
public class MarkdownBlock implements Block {

  public static final String TYPE = "markdown";
  public static final int MAX_CHAR_LENGTH = 12_000;
  private static final String TRUNCATION_SUFFIX =
    "\n\n_Response truncated due to length._";
  public static final String NEW_LINE_SEPARATOR = "\n";

  @JsonProperty("text")
  private final String text;

  @JsonCreator
  private MarkdownBlock(@JsonProperty("text") String text) {
    if (text.length() > MAX_CHAR_LENGTH) {
      this.text =
        text.substring(0, MAX_CHAR_LENGTH - TRUNCATION_SUFFIX.length()) +
        TRUNCATION_SUFFIX;
    } else {
      this.text = text;
    }
  }

  public static MarkdownBlock of(String text) {
    return new MarkdownBlock(text);
  }

  public static ImmutableList<Block> ofLongText(String text) {
    if (text.length() <= MAX_CHAR_LENGTH) {
      return ImmutableList.of(new MarkdownBlock(text));
    }

    ImmutableList.Builder<Block> blocks = ImmutableList.builder();
    String[] lines = text.split(NEW_LINE_SEPARATOR);
    StringBuilder currentBlock = new StringBuilder();

    for (String line : lines) {
      if (currentBlock.length() + line.length() + 1 > MAX_CHAR_LENGTH) {
        if (currentBlock.length() > 0) {
          blocks.add(new MarkdownBlock(currentBlock.toString()));
        }
        currentBlock = new StringBuilder(line);
      } else {
        if (currentBlock.length() > 0) {
          currentBlock.append(NEW_LINE_SEPARATOR);
        }
        currentBlock.append(line);
      }
    }

    if (currentBlock.length() > 0) {
      blocks.add(new MarkdownBlock(currentBlock.toString()));
    }

    return blocks.build();
  }

  @Override
  @JsonProperty("type")
  public String getType() {
    return TYPE;
  }

  @Override
  public Optional<String> getBlockId() {
    return Optional.empty();
  }

  @JsonProperty("text")
  public String getText() {
    return text;
  }
}

package com.hubspot.slack.client.methods.params.chat;

import com.google.common.base.Preconditions;
import com.hubspot.slack.client.methods.interceptor.HasChannel;
import com.hubspot.slack.client.models.Attachment;
import com.hubspot.slack.client.models.blocks.Block;
import com.hubspot.slack.client.models.blocks.BlockElementLengthLimits;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;

public interface MessageParams extends HasChannel {
  Optional<String> getText();

  List<Attachment> getAttachments();

  @Value.Default
  default List<Block> getBlocks() {
    return Collections.emptyList();
  }

  Optional<String> getMarkdownText();

  @Check
  default void check() {
    getMarkdownText()
      .ifPresent(markdown -> {
        Preconditions.checkState(
          markdown.length() < BlockElementLengthLimits.MAX_MARKDOWN_LENGTH.getLimit(),
          "Markdown text length exceeds the limit of " +
          BlockElementLengthLimits.MAX_MARKDOWN_LENGTH.getLimit() +
          " characters"
        );
        Preconditions.checkState(
          getText().isEmpty() && getBlocks().isEmpty(),
          "Cannot provide text or blocks when providing markdown text"
        );
      });
  }
}

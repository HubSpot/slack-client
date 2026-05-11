package com.hubspot.slack.client.methods.params.stream;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.hubspot.slack.client.methods.params.stream.chunks.StreamChunk;
import java.util.Optional;
import org.immutables.value.Value.Check;

public interface StreamParams {
  String getChannel();

  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  Optional<String> getMarkdownText();

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  ImmutableList<StreamChunk> getChunks();

  @Check
  default void checkMutuallyExclusive() {
    Preconditions.checkState(
      !(getMarkdownText().isPresent() && !getChunks().isEmpty()),
      "markdownText and chunks are mutually exclusive"
    );
  }
}

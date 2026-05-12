package com.hubspot.slack.client.methods.params.stream;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ChatAppendStreamParamsIF extends StreamParams {
  String getTs();

  @Check
  default void checkExactlyOnePresent() {
    Preconditions.checkState(
      getMarkdownText().isPresent() ^ !getChunks().isEmpty(),
      "Exactly one of markdownText or chunks must be provided"
    );
  }
}

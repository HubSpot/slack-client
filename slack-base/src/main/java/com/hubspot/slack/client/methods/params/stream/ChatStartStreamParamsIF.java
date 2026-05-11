package com.hubspot.slack.client.methods.params.stream;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ChatStartStreamParamsIF extends StreamParams {
  String getThreadTs();

  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  Optional<String> getRecipientUserId();

  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  Optional<String> getRecipientTeamId();

  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  Optional<String> getTaskDisplayMode();

  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  Optional<String> getIconEmoji();

  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  Optional<String> getIconUrl();

  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  Optional<String> getUsername();

  @Check
  default void checkNotBothPresent() {
    Preconditions.checkState(
      !(getMarkdownText().isPresent() && !getChunks().isEmpty()),
      "markdownText and chunks are mutually exclusive"
    );
  }
}

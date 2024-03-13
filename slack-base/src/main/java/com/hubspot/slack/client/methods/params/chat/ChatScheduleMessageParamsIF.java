package com.hubspot.slack.client.methods.params.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ChatScheduleMessageParamsIF extends MessageParams {
  @Override
  @JsonProperty("channel")
  String getChannelId();

  long getPostAt();

  @Default
  @JsonProperty("link_names")
  default boolean getShouldLinkNames() {
    return true;
  }

  @Default
  @JsonProperty("parse")
  default String getParseMode() {
    return "none";
  }

  @Default
  default String getThreadTs() {
    return "";
  }

  Optional<Boolean> getUnfurlLinks();

  Optional<Boolean> getUnfurlMedia();

  @Check
  default void check() {
    Preconditions.checkState(
      (getText().isPresent() && !Strings.isNullOrEmpty(getText().get())) ||
      !getAttachments().isEmpty() ||
      !getBlocks().isEmpty(),
      "Must include text if not providing attachments or blocks"
    );
  }
}

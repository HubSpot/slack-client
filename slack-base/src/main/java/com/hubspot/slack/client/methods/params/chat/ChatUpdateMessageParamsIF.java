package com.hubspot.slack.client.methods.params.chat;

import java.util.Optional;

import org.immutables.value.Value.Check;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ChatUpdateMessageParamsIF extends MessageParams {
  @JsonProperty("channel")
  String getChannelId();

  Optional<String> getText();
  String getTs();

  @Default
  @JsonProperty("link_names")
  default boolean shouldLinkNames() {
    return true;
  }

  @Default
  default String getParse() {
    return "none";
  }

  @Check
  default void check() {
    Preconditions.checkState(getText().isPresent() || !getAttachments().isEmpty(),
        "Must include text if not providing attachments");
  }
}

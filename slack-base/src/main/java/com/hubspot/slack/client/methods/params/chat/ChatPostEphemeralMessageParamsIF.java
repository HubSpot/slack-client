package com.hubspot.slack.client.methods.params.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface ChatPostEphemeralMessageParamsIF extends MessageParams {
  @Override
  @JsonProperty("channel")
  String getChannelId();

  @JsonProperty("user")
  String getUserToSendTo();

  @JsonProperty("as_user")
  Optional<Boolean> getSendAsUser();

  @JsonProperty("thread_ts")
  Optional<String> getThreadTs();

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

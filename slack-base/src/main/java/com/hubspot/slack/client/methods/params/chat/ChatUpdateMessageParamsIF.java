package com.hubspot.slack.client.methods.params.chat;

import java.util.Optional;

import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(Include.NON_EMPTY)
public interface ChatUpdateMessageParamsIF extends MessageParams {
  @JsonProperty("channel")
  String getChannelId();

  String getText();
  String getTs();

  @JsonProperty("as_user")
  Optional<Boolean> getAsUser();

  @Default
  @JsonProperty("link_names")
  default boolean shouldLinkNames() {
    return true;
  }

  @Default
  default String getParse() {
    return "none";
  }
}

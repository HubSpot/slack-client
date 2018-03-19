package com.hubspot.slack.client.methods.params.chat;

import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ChatUpdateMessageParamsIF extends MessageParams {
  @JsonProperty("channel")
  String getChannelId();

  String getText();
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
}

package com.hubspot.slack.client.methods.params.chat;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.Attachment;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ChatUnfurlParamsIF {
  @JsonProperty("channel")
  String getChannelId();

  String getTs();

  Map<String, Attachment> getUnfurls();

  Optional<Boolean> isUserAuthRequired();

  Optional<String> getUserAuthMessage();

  Optional<URI> getUserAuthUrl();
}

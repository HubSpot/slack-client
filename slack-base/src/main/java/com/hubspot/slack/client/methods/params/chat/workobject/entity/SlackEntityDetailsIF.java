package com.hubspot.slack.client.methods.params.chat.workobject.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.params.chat.workobject.ExternalRef;
import com.hubspot.slack.client.models.events.SlackEvent;
import com.hubspot.slack.client.models.events.links.Link;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackEntityDetails.class)
public interface SlackEntityDetailsIF extends SlackEvent {
  String getTriggerId();

  @JsonProperty("user")
  String getUserId();

  @JsonProperty("channel")
  String getChannelId();

  String getMessageTs();

  ExternalRef getExternalRef();

  Link getLink();
  String getAppUnfurlUrl();
}

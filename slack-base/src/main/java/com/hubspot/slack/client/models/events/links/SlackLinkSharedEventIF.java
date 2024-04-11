package com.hubspot.slack.client.models.events.links;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.events.SlackEvent;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackLinkSharedEvent.class)
public interface SlackLinkSharedEventIF extends SlackEvent {
  @JsonProperty("channel")
  String getChannelId();

  @JsonProperty("user")
  String getUserId();

  String getMessageTs();

  Optional<String> getThreadTs();

  List<Link> getLinks();

  //link_shared events do not have a ts, so we manually set it as null
  @Override
  default String getTs() {
    return null;
  }
}

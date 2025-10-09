package com.hubspot.slack.client.models.events.links;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.params.chat.workobject.ExternalRef;
import com.hubspot.slack.client.models.events.SlackEvent;
import java.util.Optional;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonDeserialize(as = EntityDetailsRequestedEvent.class)
public interface EntityDetailsRequestedEventIF extends SlackEvent {
  @JsonProperty("user")
  String getUserId();

  @JsonProperty("channel")
  Optional<String> getChannelId();

  ExternalRef getExternalRef();
  String getEntityUrl();
  Link getLink();
  String getAppUnfurlUrl();
  String getEventTs();
  String getTriggerId();
  String getUserLocale();

  Optional<String> getMessageTs();
  Optional<String> getThreadTs();

  //entity_details_requested events do not have a ts, so we manually set it as null
  @Override
  default String getTs() {
    return null;
  }
}

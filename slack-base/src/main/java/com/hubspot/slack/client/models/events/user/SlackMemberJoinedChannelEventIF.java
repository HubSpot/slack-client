package com.hubspot.slack.client.models.events.user;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.events.SlackEvent;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackMemberJoinedChannelEvent.class)
public interface SlackMemberJoinedChannelEventIF extends SlackEvent {

  @JsonProperty("user")
  String getUserId();

  @JsonProperty("channel")
  String getChannelId();

  String getChannelType();

  String getTeam();

  @JsonProperty("inviter")
  Optional<String> getInviterId();

  //Member joined events do not have a ts, so we manually set it as null
  @Override
  default String getTs() {
    return null;
  }
}

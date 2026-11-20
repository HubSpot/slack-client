package com.hubspot.slack.client.models.events.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.events.SlackEvent;
import com.hubspot.slack.client.models.users.SlackUser;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackTeamJoinEvent.class)
public interface SlackTeamJoinEventIF extends SlackEvent {
  SlackUser getUser();

  //Team join events do not have a ts, so we manually set it as null
  @Override
  default String getTs() {
    return null;
  }
}

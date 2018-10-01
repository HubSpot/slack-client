package com.hubspot.slack.client.models.events;

import java.util.List;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface SlackEventWrapperIF<T extends SlackEvent> {
  @JsonView(SlackEventView.Internal.class)
  String getToken();
  String getTeamId();
  SlackEventType getType();
  List<String> getAuthedUsers();
  String getEventId();
  String getEventTime();
  T getEvent();
}

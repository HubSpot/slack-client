package com.hubspot.slack.client.models.events;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface SlackEventWrapperIF<T extends SlackEvent> {
  String getToken();
  String getTeamId();
  Optional<String> getContextTeamId();
  SlackEventType getType();
  List<String> getAuthedUsers();
  String getEventId();
  String getEventTime();
  T getEvent();
}

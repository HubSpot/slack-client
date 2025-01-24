package com.hubspot.slack.client.models.events.assistant;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.events.SlackEvent;
import com.hubspot.slack.client.models.response.assistant.AssistantThread;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackAssistantThreadContextChangedEvent.class)
public interface SlackAssistantThreadContextChangedEventIF extends SlackEvent {
  AssistantThread getAssistantThread();

  String getEventTs();

  @Override
  default String getTs() {
    return null;
  }
}

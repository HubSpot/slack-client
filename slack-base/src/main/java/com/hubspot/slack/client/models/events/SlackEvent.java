package com.hubspot.slack.client.models.events;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hubspot.slack.client.models.events.json.EventDeserializer;

@JsonDeserialize(using = EventDeserializer.class)
public interface SlackEvent {
  SlackEventType getType();

  String getTs();
}

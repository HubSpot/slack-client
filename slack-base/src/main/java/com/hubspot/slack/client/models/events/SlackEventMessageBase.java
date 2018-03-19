package com.hubspot.slack.client.models.events;

import java.util.Optional;

public interface SlackEventMessageBase extends SlackEvent {
  Optional<SlackMessageSubtype> getSubtype();
  String getChannel();
}

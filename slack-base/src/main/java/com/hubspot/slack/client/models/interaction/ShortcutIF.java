package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.SlackChannel;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ShortcutIF extends SlackInteractiveCallback {
  String getTriggerId();

  default SlackChannel getChannel() {
    return null;
  }
}

package com.hubspot.slack.client.methods.params.assistant;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
public interface PromptIF {
  String getTitle();

  String getMessage();
}

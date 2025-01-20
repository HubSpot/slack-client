package com.hubspot.slack.client.methods.params.assistant;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasChannel;
import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface SetSuggestedPromptsParamsIF extends HasChannel {
  String getThreadTs();

  String getTitle();

  List<Prompt> getPrompts();
}

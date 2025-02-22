package com.hubspot.slack.client.methods.params.assistant;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasChannel;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface SetSuggestedPromptsParamsIF extends HasChannel {
  @Value.Derived
  default String getChannel() {
    return getChannelId();
  }

  String getThreadTs();

  Optional<String> getTitle();

  List<Prompt> getPrompts();
}

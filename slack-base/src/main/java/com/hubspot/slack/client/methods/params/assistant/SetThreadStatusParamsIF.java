package com.hubspot.slack.client.methods.params.assistant;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasChannel;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface SetThreadStatusParamsIF extends HasChannel {
  @Value.Derived
  default String getChannel() {
    return getChannelId();
  }

  String getStatus();

  String getThreadTs();
}

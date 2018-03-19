package com.hubspot.slack.client.methods.params.channels;

import java.util.Optional;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasChannel;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public abstract class AbstractChannelsInfoParams implements HasChannel {
  @Parameter
  @JsonProperty("channel")
  public abstract String getChannelId();

  public abstract Optional<Boolean> getIncludeLocale();
}

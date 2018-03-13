package com.hubspot.slack.client.methods.params.conversations;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.styles.HubSpotStyle;
import com.hubspot.slack.client.methods.TimeIntervalFilter;
import com.hubspot.slack.client.methods.interceptor.HasChannel;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public abstract class AbstractConversationsHistoryParams implements TimeIntervalFilter, HasChannel {
  public abstract Optional<Integer> getLimit();
}

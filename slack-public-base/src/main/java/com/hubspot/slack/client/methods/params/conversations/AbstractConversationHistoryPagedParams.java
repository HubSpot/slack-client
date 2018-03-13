package com.hubspot.slack.client.methods.params.conversations;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.styles.HubSpotStyle;
import com.hubspot.slack.client.methods.TimeIntervalFilter;
import com.hubspot.slack.client.methods.interceptor.HasChannel;

@Immutable
@HubSpotStyle
public abstract class AbstractConversationHistoryPagedParams implements HasChannel, TimeIntervalFilter {
  public abstract Optional<String> getCursor();
  public abstract Optional<Integer> getLimit();
}

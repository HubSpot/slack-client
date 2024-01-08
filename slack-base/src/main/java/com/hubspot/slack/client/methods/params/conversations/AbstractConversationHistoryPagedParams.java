package com.hubspot.slack.client.methods.params.conversations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.TimeIntervalFilter;
import com.hubspot.slack.client.methods.interceptor.HasChannel;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public abstract class AbstractConversationHistoryPagedParams
  implements HasChannel, TimeIntervalFilter {

  @JsonProperty("channel")
  public abstract String getChannelId();

  public abstract Optional<String> getCursor();

  public abstract Optional<Integer> getLimit();
}

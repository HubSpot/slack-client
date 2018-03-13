package com.hubspot.slack.client.models.response.channels;

import java.util.List;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.styles.HubSpotStyle;
import com.hubspot.slack.client.methods.TimeIntervalFilter;
import com.hubspot.slack.client.models.LiteMessage;
import com.hubspot.slack.client.models.response.SlackResponse;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public abstract class AbstractChannelsHistoryResponse implements SlackResponse, TimeIntervalFilter {
  @JsonProperty("has_more")
  public abstract boolean hasMore();

  public abstract List<LiteMessage> getMessages();
}

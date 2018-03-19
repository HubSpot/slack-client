package com.hubspot.slack.client.methods.params.channels;

import java.util.Optional;

import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.TimeIntervalFilter;
import com.hubspot.slack.client.methods.interceptor.HasChannel;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public abstract class AbstractChannelsHistoryParams implements HasChannel, TimeIntervalFilter  {
  @JsonProperty("channel")
  public abstract String getChannelId();

  public abstract Optional<Integer> getCount();
  @JsonProperty("is_inclusive")
  public abstract Optional<Boolean> isInclusive();

  @JsonProperty("unreads")
  public abstract Optional<Boolean> shouldIncludeUnreads();

  @Check
  public void validate() {
    Optional<Boolean> inclusive = isInclusive();
    Optional<Integer> count = getCount();

    boolean setToInclusive = inclusive.isPresent() && inclusive.get();
    boolean countIs1 = count.isPresent() && count.get() == 1;

    Preconditions.checkState(
        !setToInclusive || (setToInclusive && countIs1),
        "Because slack uses doubles as type to page on, we can't guarantee a message will show up exactly once and that we'll present every message if you try to page inclusively over a set of messages. To get around this, ensure you're not setting inclusive, and that you fetch messages within the range you want by bumping the bounds (and filtering the messages you get back)."
    );
  }
}

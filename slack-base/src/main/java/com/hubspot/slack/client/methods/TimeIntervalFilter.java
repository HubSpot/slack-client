package com.hubspot.slack.client.methods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.slack.client.methods.params.channels.PagingDirection;
import java.util.Optional;
import org.immutables.value.Value.Derived;

public interface TimeIntervalFilter {
  @JsonProperty("inclusive")
  Optional<Boolean> isInclusive();

  @JsonProperty("latest")
  Optional<String> getNewestTimestamp();

  @JsonProperty("oldest")
  Optional<String> getOldestTimestamp();

  @JsonIgnore
  @Derived
  default PagingDirection getPagingDirection() {
    if (getOldestTimestamp().isPresent() && !getNewestTimestamp().isPresent()) {
      return PagingDirection.FORWARD_IN_TIME;
    }

    return PagingDirection.BACKWARD_IN_TIME;
  }
}

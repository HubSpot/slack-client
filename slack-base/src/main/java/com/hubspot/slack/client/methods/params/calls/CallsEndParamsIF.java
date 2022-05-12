package com.hubspot.slack.client.methods.params.calls;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.primitives.Ints;
import com.hubspot.immutables.style.HubSpotStyle;
import java.time.Duration;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface CallsEndParamsIF {
  @Value.Parameter(order = 0)
  String getId();

  @JsonIgnore
  Optional<Duration> getDuration();

  @Value.Derived
  @JsonProperty("duration")
  default Optional<Integer> getDurationSeconds() {
    return getDuration()
      .map(Duration::getSeconds)
      .map(Ints::checkedCast);
  }
}

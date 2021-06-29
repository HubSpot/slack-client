package com.hubspot.slack.client.methods.params.migration;

import java.util.Optional;
import java.util.Set;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Value.Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface MigrationExchangeParamsIF {
  @JsonProperty("users")
  Set<String> getUserIds();

  Optional<String> getTeamId();

  Optional<Boolean> getToOld();
}

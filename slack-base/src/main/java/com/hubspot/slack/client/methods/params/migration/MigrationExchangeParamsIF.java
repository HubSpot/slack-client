package com.hubspot.slack.client.methods.params.migration;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Value.Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface MigrationExchangeParamsIF {
  List<String> getUsers();

  Optional<String> getTeamId();

  Optional<Boolean> getToOld();
}

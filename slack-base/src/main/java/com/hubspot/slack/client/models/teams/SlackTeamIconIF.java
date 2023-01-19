package com.hubspot.slack.client.models.teams;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Immutable;

import java.util.Optional;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface SlackTeamIconIF {

  Optional<String> getImage_88();

  @Deprecated
  Optional<String> getImageOriginal();
}

package com.hubspot.slack.client.models.auth;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.teams.SlackTeamLite;
import com.hubspot.slack.client.models.users.SlackUserLite;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public abstract class OAuthV2CredentialsIF {
  public abstract String getAccessToken();

  @JsonProperty("team")
  public abstract SlackTeamLite getSlackTeamLite();
  public abstract Optional<String> getScope();
  public abstract SlackUserLite getAuthedUser();

  public String getTeamId() {
    return getSlackTeamLite().getId();
  }

  public String getTeamName() {
    return getSlackTeamLite().getName();
  }

  public String getUserId () {
    return getAuthedUser().getId();
  }
}

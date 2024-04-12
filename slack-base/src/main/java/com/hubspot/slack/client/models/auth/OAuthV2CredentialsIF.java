package com.hubspot.slack.client.models.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.teams.SlackTeamLite;
import com.hubspot.slack.client.models.users.SlackUserLite;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public abstract class OAuthV2CredentialsIF {

  @JsonProperty("access_token")
  public abstract String getBotAccessToken();

  public abstract SlackTeamLite getTeam();

  public abstract Optional<String> getScope();

  public abstract SlackUserLite getAuthedUser();

  public abstract String getBotUserId();

  public String getTeamId() {
    return getTeam().getId();
  }

  public String getTeamName() {
    return getTeam().getName();
  }

  public String getUserId() {
    return getAuthedUser().getId();
  }

  public String getUserAccessToken() {
    return getAuthedUser().getAccessToken().get();
  }
}

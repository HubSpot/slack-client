package com.hubspot.slack.client.models.auth;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.styles.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface OAuthCredentialsIF {
  String getAccessToken();
  String getScope();
  String getTeamName();
  String getTeamId();
  String getUserId();
  Optional<BotCredentials> getBot();
  Optional<IncomingWebhook> getIncomingWebhook();
}

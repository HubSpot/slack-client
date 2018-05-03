package com.hubspot.slack.client.models.auth;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface OAuthCredentialsIF {
  String getAccessToken();
  String getTeamName();
  String getTeamId();
  Optional<String> getScope();
  Optional<String> getUserId();
  Optional<BotCredentials> getBot();
  Optional<IncomingWebhook> getIncomingWebhook();
}

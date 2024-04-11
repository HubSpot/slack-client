package com.hubspot.slack.client.models.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

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

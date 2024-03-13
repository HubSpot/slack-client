package com.hubspot.slack.client.models.response.migration;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;
import java.util.Map;
import java.util.Set;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface MigrationExchangeResponseIF extends SlackResponse {
  String getTeamId();

  String getEnterpriseId();

  Map<String, String> getUserIdMap();

  Set<String> getInvalidUserIds();
}

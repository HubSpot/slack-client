package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.views.ViewResponseBase;
import com.hubspot.slack.client.models.teams.SlackTeam;
import com.hubspot.slack.client.models.users.SlackUserLite;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface BlocksLoadOptionsRequestIF {
  String getType();

  SlackUserLite getUser();

  SlackTeam getTeam();

  String getToken();

  String getActionId();

  String getBlockId();

  String getValue();

  Container getContainer();

  Optional<ViewResponseBase> getView();
}

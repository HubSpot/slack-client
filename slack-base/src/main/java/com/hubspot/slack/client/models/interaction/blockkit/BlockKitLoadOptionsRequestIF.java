package com.hubspot.slack.client.models.interaction.blockkit;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.interaction.InteractiveLoadOptionsRequestType;
import com.hubspot.slack.client.models.response.views.ViewResponseBase;
import com.hubspot.slack.client.models.teams.SlackTeam;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface BlockKitLoadOptionsRequestIF {
    String getValue();
    ViewResponseBase getView();
    String getActionId();
    SlackTeam getTeam();
    String getToken();
    InteractiveLoadOptionsRequestType getType();
    SlackUser getUser();
}

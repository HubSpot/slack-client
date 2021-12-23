package com.hubspot.slack.client.models.interaction.blockkit;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface SlackUserIF {
    String getId();
    String getUsername();
    String getName();
    String getTeamId();
}

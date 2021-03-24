package com.hubspot.slack.client.models.events.channel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.events.conversation.SlackConversationEventCore;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackChannelSharedEvent.class)
public interface SlackChannelSharedEventIF extends SlackConversationEventCore {
    @JsonProperty("connected_team_id")
    String getConnectedTeamId();
}

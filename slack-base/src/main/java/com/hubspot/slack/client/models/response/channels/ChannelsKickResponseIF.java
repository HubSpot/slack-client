package com.hubspot.slack.client.models.response.channels;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;

@HubSpotStyle
@Value.Immutable
@JsonNaming(SnakeCaseStrategy.class)
public interface ChannelsKickResponseIF extends SlackResponse {
}

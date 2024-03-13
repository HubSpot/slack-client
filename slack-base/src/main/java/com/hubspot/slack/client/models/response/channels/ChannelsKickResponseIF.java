package com.hubspot.slack.client.models.response.channels;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
@JsonNaming(SnakeCaseStrategy.class)
public interface ChannelsKickResponseIF extends SlackResponse {}

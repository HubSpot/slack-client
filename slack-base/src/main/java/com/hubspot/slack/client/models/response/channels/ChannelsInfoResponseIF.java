package com.hubspot.slack.client.models.response.channels;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.SlackChannel;
import com.hubspot.slack.client.models.response.SlackResponse;

@Immutable
@HubSpotStyle
public interface ChannelsInfoResponseIF extends SlackResponse {
  SlackChannel getChannel();
}

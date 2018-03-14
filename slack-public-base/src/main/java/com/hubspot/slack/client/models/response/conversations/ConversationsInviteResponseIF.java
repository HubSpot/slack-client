package com.hubspot.slack.client.models.response.conversations;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.styles.HubSpotStyle;
import com.hubspot.slack.client.models.SlackChannel;
import com.hubspot.slack.client.models.response.SlackResponse;

@Immutable
@HubSpotStyle
public interface ConversationsInviteResponseIF extends SlackResponse {
  SlackChannel getChannel();
}

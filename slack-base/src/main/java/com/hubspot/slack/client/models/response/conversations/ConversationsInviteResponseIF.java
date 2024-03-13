package com.hubspot.slack.client.models.response.conversations;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.SlackChannel;
import com.hubspot.slack.client.models.response.SlackResponse;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface ConversationsInviteResponseIF extends SlackResponse {
  SlackChannel getChannel();
}

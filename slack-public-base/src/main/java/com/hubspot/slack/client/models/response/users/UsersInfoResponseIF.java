package com.hubspot.slack.client.models.response.users;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.styles.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;
import com.hubspot.slack.client.models.users.SlackUser;

@Immutable
@HubSpotStyle
public interface UsersInfoResponseIF extends SlackResponse {
  SlackUser getUser();
}

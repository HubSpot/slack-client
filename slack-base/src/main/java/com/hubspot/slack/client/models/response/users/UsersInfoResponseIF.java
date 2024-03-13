package com.hubspot.slack.client.models.response.users;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;
import com.hubspot.slack.client.models.users.SlackUser;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface UsersInfoResponseIF extends SlackResponse {
  SlackUser getUser();
}

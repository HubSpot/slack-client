package com.hubspot.slack.client.models.response.usergroups.users;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;
import com.hubspot.slack.client.models.usergroups.SlackUsergroup;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface UsergroupUsersUpdateResponseIF extends SlackResponse {
  SlackUsergroup getUsergroup();
}

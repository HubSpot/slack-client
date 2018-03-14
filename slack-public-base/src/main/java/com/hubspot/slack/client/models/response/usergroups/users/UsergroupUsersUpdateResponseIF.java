package com.hubspot.slack.client.models.response.usergroups.users;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.styles.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;
import com.hubspot.slack.client.models.usergroups.SlackUsergroup;

@Immutable
@HubSpotStyle
public interface UsergroupUsersUpdateResponseIF extends SlackResponse {
  SlackUsergroup getUsergroup();
}

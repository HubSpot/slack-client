package com.hubspot.slack.client.models.response.users;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;

@Immutable
@HubSpotStyle
public interface UsersProfileResponseIF extends SlackResponse {
  UsersProfileResponse getUserProfile();
}

package com.hubspot.slack.client.models.response.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;
import com.hubspot.slack.client.models.users.UserProfile;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface UsersProfileResponseIF extends SlackResponse {
  @JsonProperty("profile")
  UserProfile getUserProfile();
}

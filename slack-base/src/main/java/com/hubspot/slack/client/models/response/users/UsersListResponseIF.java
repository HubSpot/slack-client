package com.hubspot.slack.client.models.response.users;

import java.util.List;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;
import com.hubspot.slack.client.models.users.SlackUser;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface UsersListResponseIF extends SlackResponse {
  List<SlackUser> getMembers();
}

package com.hubspot.slack.client.models.response.conversations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;
import org.immutables.value.Value.Immutable;

import java.util.List;

@Immutable
@HubSpotStyle
public interface ConversationMemberResponseIF extends SlackResponse {
    @JsonProperty("members")
    List<String> getMember();
}

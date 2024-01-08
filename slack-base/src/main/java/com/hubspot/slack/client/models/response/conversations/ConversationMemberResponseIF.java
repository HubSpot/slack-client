package com.hubspot.slack.client.models.response.conversations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;
import java.util.List;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface ConversationMemberResponseIF extends SlackResponse {
  @JsonProperty("members")
  List<String> getMemberIds();
}

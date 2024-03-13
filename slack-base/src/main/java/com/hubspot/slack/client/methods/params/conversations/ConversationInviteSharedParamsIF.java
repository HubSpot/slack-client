package com.hubspot.slack.client.methods.params.conversations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasChannel;
import java.util.List;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface ConversationInviteSharedParamsIF extends HasChannel {
  @JsonProperty("channel")
  String getChannelId();

  List<String> getEmails();

  List<String> getUserIds();
}

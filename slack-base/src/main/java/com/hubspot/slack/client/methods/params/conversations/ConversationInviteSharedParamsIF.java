package com.hubspot.slack.client.methods.params.conversations;

import java.util.List;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasChannel;

@Immutable
@HubSpotStyle
public interface ConversationInviteSharedParamsIF extends HasChannel {

  @JsonProperty("channel")
  String getChannelId();

  List<String> getEmails();

  List<String> getUserIds();
}

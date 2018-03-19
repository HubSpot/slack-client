package com.hubspot.slack.client.models.response;

import java.util.List;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.LiteMessage;

@Immutable
@HubSpotStyle
public interface FindRepliesResponseIF extends SlackResponse {
  List<LiteMessage> getMessages();
}

package com.hubspot.slack.client.models.response.search;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.styles.HubSpotStyle;
import com.hubspot.slack.client.models.response.MessagePage;
import com.hubspot.slack.client.models.response.SlackResponse;

@Immutable
@HubSpotStyle
public interface SearchMessageResponseIF extends SlackResponse {
  String getQuery();
  MessagePage getMessages();
}

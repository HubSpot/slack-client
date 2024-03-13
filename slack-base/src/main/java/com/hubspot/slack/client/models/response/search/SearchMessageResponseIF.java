package com.hubspot.slack.client.models.response.search;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.MessagePage;
import com.hubspot.slack.client.models.response.SlackResponse;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface SearchMessageResponseIF extends SlackResponse {
  String getQuery();
  MessagePage getMessages();
}

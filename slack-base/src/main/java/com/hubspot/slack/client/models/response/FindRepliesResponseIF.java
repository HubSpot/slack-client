package com.hubspot.slack.client.models.response;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.LiteMessage;
import java.util.List;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface FindRepliesResponseIF extends SlackResponse {
  List<LiteMessage> getMessages();
}

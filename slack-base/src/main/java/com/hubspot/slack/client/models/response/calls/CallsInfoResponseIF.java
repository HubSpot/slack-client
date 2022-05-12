package com.hubspot.slack.client.models.response.calls;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.calls.SlackCall;
import com.hubspot.slack.client.models.response.SlackResponse;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
public interface CallsInfoResponseIF extends SlackResponse {
  SlackCall getCall();
}

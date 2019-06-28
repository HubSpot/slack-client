package com.hubspot.slack.client.models.response.emoji;

import java.util.Map;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;

@Immutable
@HubSpotStyle
public interface EmojiListResponseIF extends SlackResponse {
  Map<String, String> getEmoji();
}

package com.hubspot.slack.client.models.response.emoji;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.response.SlackResponse;
import java.util.Map;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface EmojiListResponseIF extends SlackResponse {
  Map<String, String> getEmoji();
}

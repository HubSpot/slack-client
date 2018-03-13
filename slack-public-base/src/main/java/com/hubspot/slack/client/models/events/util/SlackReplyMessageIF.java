package com.hubspot.slack.client.models.events.util;

import java.util.List;

import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.styles.HubSpotStyle;
import com.hubspot.slack.client.models.events.SlackEventMessageBase;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface SlackReplyMessageIF extends SlackEventMessageBase  {
  String getThreadTs();
  String getUser();
  List<Reply> getReplies();

  @Default
  default int getReplyCount() {
    return 0;
  }
}

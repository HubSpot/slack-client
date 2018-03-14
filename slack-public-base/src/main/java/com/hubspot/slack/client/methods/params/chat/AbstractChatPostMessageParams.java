package com.hubspot.slack.client.methods.params.chat;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.styles.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public abstract class AbstractChatPostMessageParams implements MessageParams {
  public abstract String getText();
  public abstract Optional<String> getThreadTs();
  public abstract Optional<String> getUsername();
  public abstract Optional<Boolean> getAsUser();
  public abstract Optional<String> getIconEmoji();
  public abstract Optional<String> getIconUrl();
  public abstract Optional<Boolean> getLinkNames();
  public abstract Optional<Boolean> getUnfurlLinks();
  public abstract Optional<Boolean> getUnfurlMedia();
  public abstract Optional<Boolean> getReplyBroadcast();
}

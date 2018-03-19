package com.hubspot.slack.client.methods.params.conversations;

import java.util.List;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Joiner;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasChannel;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ConversationInviteParamsIF extends HasChannel {
  Joiner COMMA_JOINER = Joiner.on(',').skipNulls();

  @JsonProperty("channel")
  String getChannelId();

  @JsonIgnore
  List<String> getUsers();

  @JsonProperty("users")
  default String getUsersBecauseSlackApiIsGarbage() {
    return COMMA_JOINER.join(getUsers());
  }
}

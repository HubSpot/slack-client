package com.hubspot.slack.client.methods.params.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasChannel;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface GroupsKickParamsIF extends HasChannel {
  @Override
  @JsonProperty("channel")
  String getChannelId();

  @JsonProperty("user")
  String getUserId();
}

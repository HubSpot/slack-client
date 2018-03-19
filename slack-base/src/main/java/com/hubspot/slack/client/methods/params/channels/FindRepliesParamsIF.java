package com.hubspot.slack.client.methods.params.channels;

import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.ChannelType;

@Immutable
@HubSpotStyle
public interface FindRepliesParamsIF extends ReplyQueryParams {
  @JsonProperty("channel")
  String getChannelId();

  @JsonIgnore
  @Derived
  default ChannelType getChannelType() {
    if (getChannelId().toLowerCase().startsWith("g")) {
      return ChannelType.GROUP;
    }

    return ChannelType.CHANNEL;
  }
}

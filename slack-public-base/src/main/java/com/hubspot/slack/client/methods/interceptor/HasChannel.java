package com.hubspot.slack.client.methods.interceptor;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface HasChannel {
  @JsonProperty("channel")
  String getChannelId();
}

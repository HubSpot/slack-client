package com.hubspot.slack.client.methods.params.channels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.slack.client.methods.interceptor.HasChannel;

public interface ReplyQueryParams extends HasChannel {
  @JsonProperty("thread_ts")
  String getThreadTs();
}

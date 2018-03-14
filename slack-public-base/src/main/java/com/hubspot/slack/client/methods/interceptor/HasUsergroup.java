package com.hubspot.slack.client.methods.interceptor;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface HasUsergroup {
  @JsonProperty("usergroup")
  String getUsergroupId();
}

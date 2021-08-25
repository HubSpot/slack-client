package com.hubspot.slack.client.models.response;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SlackResponse {
  boolean isOk();

  @JsonProperty("response_metadata")
  Optional<com.hubspot.slack.client.models.response.ResponseMetadata> getResponseMetadata();
}

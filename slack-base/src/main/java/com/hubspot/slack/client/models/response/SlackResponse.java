package com.hubspot.slack.client.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;

public interface SlackResponse {
  boolean isOk();

  @JsonProperty("response_metadata")
  Optional<ResponseMetadata> getResponseMetadata();
}

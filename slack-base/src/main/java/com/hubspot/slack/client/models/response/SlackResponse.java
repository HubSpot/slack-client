package com.hubspot.slack.client.models.response;

import java.util.Optional;

public interface SlackResponse {
  boolean isOk();
  Optional<ResponseMetadata> getResponseMetadata();
}

package com.hubspot.slack.client.models.users;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SlackUserCore {
  String getId();

  @JsonProperty("name")
  Optional<String> getUsername();
}

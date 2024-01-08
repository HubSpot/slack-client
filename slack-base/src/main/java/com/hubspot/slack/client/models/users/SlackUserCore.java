package com.hubspot.slack.client.models.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;

public interface SlackUserCore {
  String getId();

  @JsonProperty("name")
  Optional<String> getUsername();
}

package com.hubspot.slack.client.models;

import java.util.Optional;

public interface SlackChannelBase {
  String getId();
  String getName();

  Optional<Boolean> getIsArchived();
}

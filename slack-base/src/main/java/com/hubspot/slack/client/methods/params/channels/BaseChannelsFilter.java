package com.hubspot.slack.client.methods.params.channels;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.immutables.value.Value.Default;

public interface BaseChannelsFilter {
  // should we exclude archived channels?
  // Slack's API is... special so this actually excludes archived channels based on if this param is present,
  // not based on the value of the param. Submitted a bug report with Slack
  @JsonProperty("exclude_archived")
  Optional<Boolean> shouldExcludeArchived();

  // should we exclude the members section from the response?
  @Default
  @JsonProperty("exclude_members")
  default boolean shouldExcludeMembers() {
    return false;
  }
}

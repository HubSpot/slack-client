package com.hubspot.slack.client.methods.interceptor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.immutables.value.Value.Derived;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

public interface HasCommaSeperatedChannelIds {
  @JsonIgnore
  List<String> getRawChannelIds();

  @Derived
  @JsonProperty("channels")
  default Optional<String> getEncodedChannels() {
    String commaSeparatedList = getRawChannelIds().stream()
        .collect(Collectors.joining(","));
    return Optional.ofNullable(Strings.emptyToNull(commaSeparatedList));
  }
}

package com.hubspot.slack.client.methods.interceptor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.immutables.value.Value.Derived;

public interface HasCommaSeperatedUserIds {
  @JsonIgnore
  List<String> getRawUserIds();

  @Derived
  @JsonProperty("users")
  default Optional<String> getEncodedUsers() {
    String commaSeparatedList = getRawUserIds().stream().collect(Collectors.joining(","));
    return Optional.ofNullable(Strings.emptyToNull(commaSeparatedList));
  }
}

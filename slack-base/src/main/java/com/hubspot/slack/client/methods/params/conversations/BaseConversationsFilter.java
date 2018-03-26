package com.hubspot.slack.client.methods.params.conversations;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.immutables.value.Value.Derived;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.slack.client.models.conversations.ConversationType;

public interface BaseConversationsFilter {
  @JsonProperty("exclude_archived")
  Optional<Boolean> shouldExcludeArchived();

  @Derived
  default String getTypes() {
    return getConversationTypes().stream()
        .map(ConversationType::toString)
        .collect(Collectors.joining(","));
  }

  @JsonIgnore
  Set<ConversationType> getConversationTypes();
}

package com.hubspot.slack.client.methods.params.conversations;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ConversationsListParamsIF extends BaseConversationsFilter {
  Optional<String> getCursor();
  Optional<Integer> getLimit();
  Optional<Boolean> shouldExcludeSharedChannels();
}

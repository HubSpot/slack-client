package com.hubspot.slack.client.models.conversations;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ConversationIF {
  String getId();
  Optional<String> getName();
  @JsonProperty("is_channel")
  Optional<Boolean> isChannel();
  @JsonProperty("is_group")
  Optional<Boolean> isGroup();
  @JsonProperty("is_mpim")
  Optional<Boolean> isMpim();
  @JsonProperty("is_im")
  Optional<Boolean> isIm();
  @JsonProperty("is_archived")
  Optional<Boolean> isArchived();
  @JsonProperty("is_general")
  Optional<Boolean> isGeneral();
  @JsonProperty("is_private")
  Optional<Boolean> isPrivate();
  @JsonProperty("is_member")
  Optional<Boolean> isMember();
  @JsonProperty("is_shared")
  Optional<Boolean> isShared();
  @JsonProperty("num_members")
  Optional<Integer> getNumMembers();
}

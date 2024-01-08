package com.hubspot.slack.client.models.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.ChannelMetadata;
import com.hubspot.slack.client.models.Message;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface SlackGroupIF {
  String getId();
  String getName();

  @JsonProperty("created")
  long getCreatedEpochSeconds();

  @JsonProperty("creator")
  String getCreatorId();

  @JsonProperty("is_archived")
  boolean isArchived();

  @JsonProperty("is_mpim")
  boolean isMpim();

  @JsonProperty("members")
  List<String> getMemberIds();

  ChannelMetadata getTopic();
  ChannelMetadata getPurpose();

  Optional<Message> getLatest();
}

package com.hubspot.slack.client.models;

import java.util.Optional;

import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface SlackChannelIF {
  String getId();
  String getName();
  Optional<Boolean> getIsArchived();
  Optional<Boolean> getIsGeneral();

  @Derived
  @JsonIgnore
  default ChannelType getChannelType() {
    if (getId().toLowerCase().startsWith("g")) {
      return ChannelType.GROUP;
    }

    return ChannelType.CHANNEL;
  }
}

package com.hubspot.slack.client.methods.params.usergroups;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasCommaSeperatedChannelIds;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(Include.NON_EMPTY)
public interface UsergroupCreateParamsIF extends HasCommaSeperatedChannelIds {
  String getName();

  Optional<String> getDescription();
  Optional<String> getHandle();
  Optional<Boolean> getIncludeCount();
}

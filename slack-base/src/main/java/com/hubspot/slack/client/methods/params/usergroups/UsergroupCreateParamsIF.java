package com.hubspot.slack.client.methods.params.usergroups;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasCommaSeperatedChannelIds;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

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

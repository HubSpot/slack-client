package com.hubspot.slack.client.models.usergroups;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.List;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface UsergroupPreferencesIF {
  @JsonProperty("channels")
  List<String> getChannelIdsUsersAutojoin();

  @JsonProperty("groups")
  List<String> getGroupsUsersAutojoin();
}

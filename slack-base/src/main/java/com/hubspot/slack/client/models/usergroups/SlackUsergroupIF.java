package com.hubspot.slack.client.models.usergroups;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface SlackUsergroupIF {
  String getId();

  String getTeamId();

  @JsonProperty("is_usergroup")
  boolean isUsergroup();

  String getName();

  Optional<String> getDescription();

  String getHandle();

  @JsonProperty("is_external")
  boolean isExternal();

  @JsonProperty("date_create")
  int getDateCreatedEpochSeconds();

  @JsonProperty("date_update")
  int getDateUpdatedEpochSeconds();

  @JsonProperty("date_delete")
  int getDateDeletedEpochSeconds(); // 0 means never

  Optional<String> getAutoType();

  Optional<String> getCreatedBy();

  Optional<String> getUpdatedBy();

  Optional<String> getDeletedBy();

  @Derived
  default boolean isDeleted() {
    return getDeletedBy().isPresent();
  }

  @JsonProperty("prefs")
  UsergroupPreferences getPreferences();

  @JsonProperty("users")
  List<String> getUserIdsInGroup();

  Optional<Integer> getUserCount();
}

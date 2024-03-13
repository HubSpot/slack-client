package com.hubspot.slack.client.methods.params.usergroups.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasCommaSeperatedUserIds;
import com.hubspot.slack.client.methods.interceptor.HasUsergroup;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(Include.NON_EMPTY)
public abstract class AbstractUsergroupUsersUpdateParams
  implements HasCommaSeperatedUserIds, HasUsergroup {

  @JsonProperty("usergroup")
  public abstract String getUsergroupId();

  public abstract Optional<Boolean> getIncludeCount();
}

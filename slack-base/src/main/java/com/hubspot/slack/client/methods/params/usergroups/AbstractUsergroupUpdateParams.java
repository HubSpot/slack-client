package com.hubspot.slack.client.methods.params.usergroups;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasCommaSeperatedChannelIds;
import com.hubspot.slack.client.methods.interceptor.HasUsergroup;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(Include.NON_EMPTY)
public abstract class AbstractUsergroupUpdateParams
  implements HasCommaSeperatedChannelIds, HasUsergroup {

  @JsonProperty("usergroup")
  public abstract String getUsergroupId();

  public abstract Optional<String> getName();

  public abstract Optional<String> getDescription();

  public abstract Optional<String> getHandle();

  public abstract Optional<Boolean> getIncludeCount();
}

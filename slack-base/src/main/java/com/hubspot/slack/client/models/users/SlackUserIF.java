package com.hubspot.slack.client.models.users;

import java.util.Optional;

import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface SlackUserIF extends SlackUserCore {
  Optional<UserProfile> getProfile();
  Optional<Boolean> isDeleted();
  Optional<String> getColor();
  Optional<String> isAdmin();
  Optional<String> isOwner();
  Optional<String> getTeamId();
  Optional<String> getRealName();
  Optional<Boolean> isPrimaryOwner();
  Optional<Boolean> isRestricted();
  Optional<Boolean> isUltraRestricted();
  Optional<Boolean> isBot();
  Optional<Boolean> isAppUser();

  @JsonProperty("tz")
  Optional<String> getTimezone();

  @JsonProperty("tz_label")
  Optional<String> getTimezoneLabel();

  @JsonProperty("tz_offset")
  Optional<String> getTimezoneOffset();

  @JsonProperty("updated")
  Optional<Integer> getRawUpdated();

  Optional<String> getLocale();

  @Derived
  default Optional<Long> getUpdatedAt() {
    return getRawUpdated().map(updated -> updated * 1000L);
  }

}

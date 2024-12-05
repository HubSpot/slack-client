package com.hubspot.slack.client.models.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface SlackUserIF extends SlackUserCore {
  Optional<UserProfile> getProfile();

  @JsonProperty("deleted")
  Optional<Boolean> isDeleted();

  Optional<String> getColor();

  Optional<String> isAdmin();

  Optional<String> isOwner();

  Optional<String> getTeamId();

  Optional<String> getRealName();

  @JsonSetter("primaryOwner")
  @JsonProperty("is_primary_owner")
  Optional<Boolean> isPrimaryOwner();

  @JsonSetter("restricted")
  @JsonProperty("is_restricted")
  Optional<Boolean> isRestricted();

  @JsonSetter("ultra_restricted")
  @JsonProperty("is_ultra_restricted")
  Optional<Boolean> isUltraRestricted();

  @JsonProperty("is_bot")
  Optional<Boolean> isBot();

  @JsonSetter("app_user")
  @JsonProperty("is_app_user")
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

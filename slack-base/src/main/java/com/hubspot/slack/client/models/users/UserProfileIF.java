package com.hubspot.slack.client.models.users;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface UserProfileIF {
  @JsonProperty("display_name")
  Optional<String> getUsername();

  @JsonProperty("display_name_normalized")
  Optional<String> getUsernameNormalized();

  Optional<String> getRealName();
  Optional<String> getRealNameNormalized();
  Optional<String> getEmail();
  Optional<String> getStatusText();
  Optional<String> getStatusEmoji();
  Optional<String> getTitle();
  Optional<String> getPhone();
  Optional<String> getSkype();

  @JsonProperty("team")
  Optional<String> getTeamId();

  Optional<String> getAvatarHash();
  Optional<String> getImage24();
  Optional<String> getImage32();
  Optional<String> getImage48();
  Optional<String> getImage72();
  Optional<String> getImage192();
  Optional<String> getImage512();
  Optional<String> getImage1024();
  Optional<String> getImageOriginal();
}

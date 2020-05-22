package com.hubspot.slack.client.models.users;

import java.util.Map;
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

  // Extra custom fields set by your workspace admin
  Optional<Map<String, ProfileField>> getFields();

  @JsonProperty("team")
  Optional<String> getTeamId();

  Optional<String> getAvatarHash();
  Optional<String> getImageOriginal();

  @JsonProperty("image_24")
  Optional<String> getImage24();
  @JsonProperty("image_32")
  Optional<String> getImage32();
  @JsonProperty("image_48")
  Optional<String> getImage48();
  @JsonProperty("image_72")
  Optional<String> getImage72();
  @JsonProperty("image_192")
  Optional<String> getImage192();
  @JsonProperty("image_512")
  Optional<String> getImage512();
  @JsonProperty("image_1024")
  Optional<String> getImage1024();
}

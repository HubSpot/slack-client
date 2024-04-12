package com.hubspot.slack.client.methods.params.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasUser;
import com.hubspot.slack.client.models.users.UserProfile;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface SetUserProfileParamsIF extends HasUser {
  @Override
  @Value.Parameter
  @JsonProperty("user")
  String getUserId();

  @JsonProperty("profile")
  Optional<UserProfile> getUserProfile();

  @JsonProperty("name")
  Optional<String> getName();

  @JsonProperty("value")
  Optional<String> getValue();
}

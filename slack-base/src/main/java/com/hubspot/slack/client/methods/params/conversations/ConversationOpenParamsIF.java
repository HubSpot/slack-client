package com.hubspot.slack.client.methods.params.conversations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Joiner;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ConversationOpenParamsIF {
  Joiner COMMA_JOINER = Joiner.on(',').skipNulls();

  @JsonProperty("channel")
  Optional<String> getChannelId();

  @JsonIgnore
  List<String> getUsers();

  @JsonProperty("users")
  default String getUsersString() {
    return COMMA_JOINER.join(getUsers());
  }

  @Default
  default boolean getReturnIm() {
    return true;
  }
}

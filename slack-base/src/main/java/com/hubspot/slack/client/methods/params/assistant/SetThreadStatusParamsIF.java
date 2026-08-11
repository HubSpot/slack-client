package com.hubspot.slack.client.methods.params.assistant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.immutables.validation.ImmutableConditions;
import com.hubspot.slack.client.methods.interceptor.HasChannel;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface SetThreadStatusParamsIF extends HasChannel {
  int MAX_LOADING_MESSAGES = 10;

  @Value.Derived
  default String getChannel() {
    return getChannelId();
  }

  String getStatus();

  String getThreadTs();

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  List<String> getLoadingMessages();

  Optional<String> getIconEmoji();

  Optional<String> getIconUrl();

  Optional<String> getUsername();

  @Value.Check
  default void loadingMessagesWithinLimit() {
    ImmutableConditions.checkValid(
      getLoadingMessages().size() <= MAX_LOADING_MESSAGES,
      "loadingMessages cannot contain more than %s messages",
      MAX_LOADING_MESSAGES
    );
  }
}

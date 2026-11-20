package com.hubspot.slack.client.models.events.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.events.SlackEvent;
import com.hubspot.slack.client.models.users.SlackUser;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

/**
 * Sent when a user's profile fields change.
 *
 * @see <a href="https://docs.slack.dev/reference/events/user_profile_changed/">user_profile_changed event</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackUserProfileChangedEvent.class)
public interface SlackUserProfileChangedEventIF extends SlackEvent {
  SlackUser getUser();

  Optional<String> getEventTs();

  Optional<Long> getCacheTs();

  //User profile changed events do not have a ts, so we manually set it as null
  @Override
  default String getTs() {
    return null;
  }
}

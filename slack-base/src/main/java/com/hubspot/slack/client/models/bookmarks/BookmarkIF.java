package com.hubspot.slack.client.models.bookmarks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.time.Instant;
import java.util.Optional;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface BookmarkIF {
  BookmarkType getType();
  String getId();
  String getChannelId();
  String getTitle();

  Optional<String> getLink();
  Optional<String> getEmoji();
  Optional<String> getIconUrl();
  Optional<String> getEntityId();

  @JsonProperty("date_created")
  long getDateCreatedEpochSeconds();

  @JsonIgnore
  @Value.Derived
  default Instant getCreatedAt() {
    return Instant.ofEpochSecond(getDateCreatedEpochSeconds());
  }

  @JsonProperty("date_updated")
  long getDateUpdatedEpochSeconds();

  @JsonIgnore
  @Value.Derived
  default Instant getUpdatedAt() {
    return Instant.ofEpochSecond(getDateUpdatedEpochSeconds());
  }

  Optional<String> getRank();
  Optional<String> getLastUpdatedByUserId();
  Optional<String> getLastUpdatedByTeamId();
  Optional<String> getShortcutId();
  Optional<String> getAppId();
}

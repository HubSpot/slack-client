package com.hubspot.slack.client.methods.params.bookmarks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasChannel;
import com.hubspot.slack.client.models.bookmarks.BookmarkType;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface BookmarksAddParamsIF extends HasChannel {
  @Value.Derived
  default String getChannel() {
    return getChannelId();
  }

  String getTitle();

  BookmarkType getType();

  Optional<String> getEmoji();

  Optional<String> getEntityId();

  Optional<String> getLink();

  Optional<String> getParentId();
}

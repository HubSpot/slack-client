package com.hubspot.slack.client.methods.params.bookmarks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasChannel;
import com.hubspot.slack.client.models.bookmarks.BookmarkType;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface BookmarksEditParamsIF extends HasChannel {
  String getBookmarkId();

  Optional<String> getEmoji();
  Optional<String> getLink();
  Optional<String> getTitle();
}

package com.hubspot.slack.client.models.response.bookmarks;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.bookmarks.Bookmark;
import com.hubspot.slack.client.models.response.SlackResponse;
import java.util.List;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
public interface BookmarkListResponseIF extends SlackResponse {
  List<Bookmark> getBookmarks();
}

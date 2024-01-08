package com.hubspot.slack.client.models.response.bookmarks;

import com.hubspot.slack.client.TestMappers;
import com.hubspot.slack.client.models.JsonLoader;
import org.junit.Test;

public class BookmarkListResponseIFTest {

  @Test
  public void itDoesDeserializeBookmarkListResponse() throws Exception {
    String rawJson = JsonLoader.loadJsonFromFile("bookmark_list_result.json");

    BookmarkListResponse response = TestMappers.OBJECT_MAPPER.readValue(
      rawJson,
      BookmarkListResponse.class
    );
    response.getBookmarks();
  }
}

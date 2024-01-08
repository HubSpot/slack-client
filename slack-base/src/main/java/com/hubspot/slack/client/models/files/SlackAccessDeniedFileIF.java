package com.hubspot.slack.client.models.files;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackAccessDeniedFile.class)
public interface SlackAccessDeniedFileIF extends SlackFile {
  String getFileAccess();

  @Override
  @Default
  default String getId() {
    return "unknown";
  }

  @Override
  @Default
  default long getCreatedEpochSeconds() {
    return -1;
  }

  @Override
  @Default
  default long getTimestampEpochSeconds() {
    return -1;
  }

  @Override
  @Default
  default String getName() {
    return "unknown";
  }

  @Override
  @Default
  default String getTitle() {
    return "unknown";
  }

  @Override
  @Default
  default String getMimetype() {
    return "unknown";
  }

  @Override
  @Default
  default SlackFileType getFiletype() {
    return SlackFileType.UNKNOWN;
  }

  @Override
  @Default
  default String getPrettyType() {
    return "unknown";
  }

  @Override
  @Default
  default String getUserId() {
    return "unknown";
  }

  @Override
  @Default
  default boolean isEditable() {
    return false;
  }

  @Override
  @Default
  default long getSize() {
    return -1;
  }

  @Override
  @Default
  default String getMode() {
    return "unknown";
  }

  @Override
  @Default
  default boolean isExternal() {
    return false;
  }

  @Override
  @Default
  default boolean isPublic() {
    return false;
  }

  @Override
  @Default
  default boolean isPublicUrlShared() {
    return false;
  }

  @Override
  @Default
  default boolean getDisplayAsBot() {
    return false;
  }

  @Override
  @Default
  default String getUsername() {
    return "unknown";
  }

  @Override
  @Default
  default String getUrlPrivate() {
    return "unknown";
  }

  @Override
  Optional<String> getUrlPrivateDownload();

  @Override
  @Default
  default String getPermalink() {
    return "unknown";
  }

  @Override
  Optional<String> getPermalinkPublic();

  @Override
  @Default
  default int getCommentsCount() {
    return 0;
  }

  @Override
  Optional<Boolean> isStarred();

  @Override
  List<String> getChannelIds();

  @Override
  List<String> getGroupIds();

  @Override
  List<String> getImIds();
}

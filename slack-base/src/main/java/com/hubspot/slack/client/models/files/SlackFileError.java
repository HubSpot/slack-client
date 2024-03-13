package com.hubspot.slack.client.models.files;

import java.util.List;
import java.util.Optional;
import org.immutables.value.Value;

public interface SlackFileError extends SlackFile {
  String getFileAccess();

  @Override
  @Value.Default
  default String getId() {
    return "unknown";
  }

  @Override
  @Value.Default
  default long getCreatedEpochSeconds() {
    return -1;
  }

  @Override
  @Value.Default
  default long getTimestampEpochSeconds() {
    return -1;
  }

  @Override
  @Value.Default
  default String getName() {
    return "unknown";
  }

  @Override
  @Value.Default
  default String getTitle() {
    return "unknown";
  }

  @Override
  @Value.Default
  default String getMimetype() {
    return "unknown";
  }

  @Override
  @Value.Default
  default SlackFileType getFiletype() {
    return SlackFileType.UNKNOWN;
  }

  @Override
  @Value.Default
  default String getPrettyType() {
    return "unknown";
  }

  @Override
  @Value.Default
  default String getUserId() {
    return "unknown";
  }

  @Override
  @Value.Default
  default boolean isEditable() {
    return false;
  }

  @Override
  @Value.Default
  default long getSize() {
    return -1;
  }

  @Override
  @Value.Default
  default String getMode() {
    return "unknown";
  }

  @Override
  @Value.Default
  default boolean isExternal() {
    return false;
  }

  @Override
  @Value.Default
  default boolean isPublic() {
    return false;
  }

  @Override
  @Value.Default
  default boolean isPublicUrlShared() {
    return false;
  }

  @Override
  @Value.Default
  default boolean getDisplayAsBot() {
    return false;
  }

  @Override
  @Value.Default
  default String getUsername() {
    return "unknown";
  }

  @Override
  @Value.Default
  default String getUrlPrivate() {
    return "unknown";
  }

  @Override
  Optional<String> getUrlPrivateDownload();

  @Override
  @Value.Default
  default String getPermalink() {
    return "unknown";
  }

  @Override
  Optional<String> getPermalinkPublic();

  @Override
  @Value.Default
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

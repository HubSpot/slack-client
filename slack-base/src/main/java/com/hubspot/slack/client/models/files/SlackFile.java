package com.hubspot.slack.client.models.files;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hubspot.slack.client.models.files.json.SlackFileDeserializer;
import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Default;

@JsonDeserialize(using = SlackFileDeserializer.class)
public interface SlackFile {
  String getId();
  @JsonProperty("created")
  long getCreatedEpochSeconds();
  @JsonProperty("timestamp")
  long getTimestampEpochSeconds();
  String getName();
  String getTitle();
  String getMimetype();
  SlackFileType getFiletype();
  String getPrettyType();

  @JsonProperty("user")
  String getUserId();

  boolean isEditable();
  long getSize();

  String getMode();
  @JsonProperty("is_external")
  boolean isExternal();
  @JsonProperty("is_public")
  boolean isPublic();
  boolean isPublicUrlShared();
  boolean getDisplayAsBot();
  String getUsername();
  String getUrlPrivate();
  Optional<String> getUrlPrivateDownload();

  String getPermalink();
  Optional<String> getPermalinkPublic();

  @Default
  default int getCommentsCount() {
    return 0;
  }

  @JsonProperty("is_starred")
  Optional<Boolean> isStarred();

  @JsonProperty("channels")
  List<String> getChannelIds();
  @JsonProperty("groups")
  List<String> getGroupIds();
  @JsonProperty("ims")
  List<String> getImIds();
}

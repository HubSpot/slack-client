package com.hubspot.slack.client.models.files;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Default;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(
    use = Id.NAME,
    include = As.EXISTING_PROPERTY,
    property = "filetype",
    defaultImpl = SlackUnknownFiletype.class
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = SlackTextFile.class, name = "text"),
    @JsonSubTypes.Type(value = SlackCsvFile.class, name = "csv"),
    @JsonSubTypes.Type(value = SlackGifFile.class, name = "gif"),
    @JsonSubTypes.Type(value = SlackJpgFile.class, name = "jpg"),
    @JsonSubTypes.Type(value = SlackPngFile.class, name = "png"),
    @JsonSubTypes.Type(value = SlackJavaScriptFile.class, name = "javascript")
})
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

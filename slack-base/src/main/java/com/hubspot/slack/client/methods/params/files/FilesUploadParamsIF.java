package com.hubspot.slack.client.methods.params.files;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.files.SlackFileType;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface FilesUploadParamsIF {
  List<String> getChannels();
  Optional<String> getContent();
  Optional<File> getFile();
  Optional<String> getFilename();
  Optional<SlackFileType> getFiletype();
  Optional<String> getInitialComment();
  Optional<String> getThreadTs();
  Optional<String> getTitle();

  @Check
  @JsonIgnore
  default void hasContent() {
    if (!getContent().isPresent() && !getFile().isPresent()) {
      throw new IllegalStateException("Must provide either content or raw file");
    }
  }
}
